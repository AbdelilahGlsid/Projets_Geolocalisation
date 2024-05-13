package com.example.bouhkka_abdelilah_tp5_gps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MyApp() {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val mapProperties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }
    val defaultLocation = LatLng(37.7749, -122.4194)
    val defaultCameraPosition = CameraPosition.Builder()
        .target(defaultLocation)
        .zoom(10f)
        .build()
    val cameraPositionState = rememberCameraPositionState(defaultCameraPosition)

    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    if (!locationPermissionState.permission) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Location permission required to access GPS.")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { locationPermissionState.launchPermissionRequest() }) {
                Text("Grant Permission")
            }
        }
    } else {
        var lastKnownLocation by remember { mutableStateOf<LatLng?>(null) }

        LaunchedEffect(Unit) {
            if (ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return@LaunchedEffect
            }

            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    lastKnownLocation = LatLng(it.latitude, it.longitude)
                    cameraPositionState.move(
                        CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 15f)
                    )
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.weight(1f),
                properties = mapProperties,
                cameraPositionState = cameraPositionState
            ) {
                lastKnownLocation?.let {
                    Marker(position = it, title = "You are here")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CaptureAndSharePhoto(context)
                Button(onClick = {
                    // Handle viewing and recording observations
                }) {
                    Text("Record Observation")
                }
                Button(onClick = {
                    ChallengesAndRewards()
                }) {
                    Text("Challenges")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            HikingJournal(context)
        }
    }
}


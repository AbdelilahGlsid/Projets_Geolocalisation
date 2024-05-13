package com.example.bouhkka_abdelilah_tp5_gps

import android.content.Context
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.LocationServices

@Composable
fun HikingJournal(context: Context) {
    val hikes = remember { mutableStateListOf<Hike>() }

    Column(modifier = Modifier.padding(16.dp)) {
        hikes.forEach { hike ->
            Text("Hike on ${hike.date}: ${hike.distance} km, ${hike.elevation} m elevation gain")
        }

        Button(onClick = {
            val currentLocation = getCurrentLocation(context)
            currentLocation?.let {
                hikes.add(Hike(it.latitude, it.longitude, System.currentTimeMillis(), 0.0, 0.0))
            }
        }) {
            Text("Start New Hike")
        }
    }
}

data class Hike(val latitude: Double, val longitude: Double, val date: Long, val distance: Double, val elevation: Double)

fun getCurrentLocation(context: Context): Location? {
    // Obtain the current location using FusedLocationProviderClient
    return null
}

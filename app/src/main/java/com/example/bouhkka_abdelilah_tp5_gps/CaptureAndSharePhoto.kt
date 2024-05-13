package com.example.bouhkka_abdelilah_tp5_gps

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CaptureAndSharePhoto(context: Context) {
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        bitmap?.let {
            val path = MediaStore.Images.Media.insertImage(context.contentResolver, it, "Title", null)
            imageUri.value = Uri.parse(path)
            // Now you can share the photo using the imageUri
        }
    }

    Column {
        Button(onClick = { launcher.launch(null) }) {
            Text("Capture Photo")
        }

        imageUri.value?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "image/*"
                    putExtra(Intent.EXTRA_STREAM, it)
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
            }) {
                Text("Share Photo")
            }
        }
    }
}

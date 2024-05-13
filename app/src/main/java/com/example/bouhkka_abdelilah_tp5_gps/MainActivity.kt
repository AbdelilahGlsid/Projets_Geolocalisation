package com.example.bouhkka_abdelilah_tp5_gps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.bouhkka_abdelilah_tp5_gps.ui.theme.Bouhkka_abdelilah_TP5_GPSTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Bouhkka_abdelilah_TP5_GPSTheme {
                MyApp()
            }
        }
    }
}

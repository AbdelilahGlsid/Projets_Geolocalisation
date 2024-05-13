package com.example.bouhkka_abdelilah_tp5_gps

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChallengesAndRewards() {
    val challenges = listOf(
        "Hike 10 km in a week",
        "Reach the highest peak in your area",
        "Spot 5 different bird species"
    )

    Column(modifier = Modifier.padding(16.dp)) {
        challenges.forEach { challenge ->
            Text("Challenge: $challenge")
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

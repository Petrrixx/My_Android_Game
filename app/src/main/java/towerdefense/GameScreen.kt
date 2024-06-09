package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex

@Composable
fun GameScreen(
    rendererContent: @Composable () -> Unit,
    castleHealth: String,
    level: String,
    time: String,
    coins: String,
    specialCoins: String,
    onMenuClick: () -> Unit,
    joystickContent: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        rendererContent()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .zIndex(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Castle Health: $castleHealth", color = Color.White)
                    Text("Level: $level", color = Color.White)
                    Text("Time: $time", color = Color.White)
                    Text("Coins: $coins", color = Color.White)
                    Text("Special Coins: $specialCoins", color = Color.White)
                }
                Button(
                    onClick = onMenuClick,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Menu")
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .zIndex(2f),
            contentAlignment = Alignment.BottomStart
        ) {
            joystickContent()
        }
    }
}

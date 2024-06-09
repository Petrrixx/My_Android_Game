package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(onReturnClick: () -> Unit, onSettingsClick: () -> Unit, onExitClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onReturnClick, modifier = Modifier.padding(8.dp)) {
                Text("Return")
            }
            Button(onClick = onSettingsClick, modifier = Modifier.padding(8.dp)) {
                Text("Settings")
            }
            Button(onClick = onExitClick, modifier = Modifier.padding(8.dp)) {
                Text("Exit")
            }
        }
    }
}

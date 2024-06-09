package com.example.towerdefense_seminar_bolecek_peter_5zyi24
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OptionsScreen(onReturnClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Settings", modifier = Modifier.padding(8.dp))
            Button(onClick = onReturnClick, modifier = Modifier.padding(8.dp)) {
                Text("Return")
            }
        }
    }
}

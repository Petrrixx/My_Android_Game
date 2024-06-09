package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme

class OptionsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                OptionsScreen(onReturnClick = { finish() })
            }
        }
    }
}

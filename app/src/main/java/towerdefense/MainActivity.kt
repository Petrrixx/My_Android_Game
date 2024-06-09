package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen(
                    onStartClick = {
                        val intent = Intent(this, GameActivity::class.java)
                        startActivity(intent)
                    },
                    onSettingsClick = {
                        val intent = Intent(this, OptionsActivity::class.java)
                        startActivity(intent)
                    },
                    onExitClick = {
                        finishAffinity()
                    }
                )
            }
        }
    }
}

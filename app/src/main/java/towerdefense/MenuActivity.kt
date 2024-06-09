package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MenuScreen(
                    onReturnClick = { finish() },
                    onSettingsClick = {
                        val intent = Intent(this, OptionsActivity::class.java)
                        startActivity(intent)
                    },
                    onExitClick = {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

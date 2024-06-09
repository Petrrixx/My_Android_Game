package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class GameActivity : ComponentActivity() {
    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val castleHealth = viewModel.castleHealth.collectAsState()
            val level = viewModel.level.collectAsState()
            val time = viewModel.time.collectAsState()
            val coins = viewModel.coins.collectAsState()
            val specialCoins = viewModel.specialCoins.collectAsState()

            MaterialTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    OpenGLRendererView(context = this@GameActivity)

                    Column(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(16.dp)
                    ) {
                        Text("Castle Health: ${castleHealth.value}")
                        Text("Level: ${level.value}")
                        Text("Time: ${time.value}")
                        Text("Coins: ${coins.value}")
                        Text("Special Coins: ${specialCoins.value}")
                    }

                    Button(
                        onClick = {
                            val intent = Intent(this@GameActivity, MenuActivity::class.java)
                            startActivity(intent)
                        },
                        modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
                    ) {
                        Text("Menu")
                    }

                    Joystick(
                        onDirectionChange = { up, down, left, right, upLeft, upRight, downLeft, downRight ->
                            viewModel.updateDirections(up, down, left, right, upLeft, upRight, downLeft, downRight)
                        },
                        modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
                    )
                }
            }
        }
    }
}

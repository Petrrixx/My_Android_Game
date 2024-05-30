package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private lateinit var openGLView: OpenGLView
    private lateinit var player: Player
    private lateinit var enemies: List<Enemy>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        openGLView = findViewById(R.id.opengl_view)

        player = Player(x = 0.5f, y = 0.5f)
        player.loadTextures(context = this)

        enemies = listOf(
            Enemy(x = 1.0f, y = 0.5f),
            Enemy(x = 1.0f, y = 0.6f)
        )

        enemies.forEach { it.loadTextures(context = this) }

        val returnButton: Button = findViewById(R.id.return_button)
        returnButton.setOnClickListener {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        openGLView.onPause()
    }

    override fun onResume() {
        super.onResume()
        openGLView.onResume()
    }
}

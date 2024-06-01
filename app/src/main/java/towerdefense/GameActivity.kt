package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private lateinit var openGLView: OpenGLView
    private lateinit var renderer: OpenGLRenderer
    private lateinit var joystick: Joystick

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        hideSystemUI()

        openGLView = findViewById(R.id.opengl_view)
        joystick = findViewById(R.id.joystick)

        renderer = openGLView.getRenderer()

        renderer.setEnemies(listOf(
            Enemy(x = 1.0f, y = 0.5f, type = EnemyType.TYPE1),
            Enemy(x = 1.0f, y = 0.6f, type = EnemyType.TYPE1)
        ))

        openGLView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    val joystickX = event.x
                    val joystickY = event.y
                    joystick.visibility = View.VISIBLE
                    joystick.setCenter(joystickX, joystickY)
                    joystick.onTouchEvent(event)
                    renderer.getPlayer().handleTouch(event.x, event.y)
                }
                MotionEvent.ACTION_UP -> {
                    joystick.visibility = View.GONE
                }
            }
            true
        }

        val menuButton: Button = findViewById(R.id.menu_button)
        menuButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        openGLView.onPause()
    }

    override fun onResume() {
        super.onResume()
        openGLView.onResume()
        hideSystemUI()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        actionBar?.hide()
    }
}

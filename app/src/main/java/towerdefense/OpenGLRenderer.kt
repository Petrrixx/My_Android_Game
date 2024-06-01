package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

// render engine v2.1 - Petrrixx { Peter Boleček } - github
class OpenGLRenderer(private val context: Context) : GLSurfaceView.Renderer {
    private lateinit var player: Player
    private val enemies = mutableListOf<Enemy>()
    private var width = 0
    private var height = 0
    private var joystickX = 0f
    private var joystickY = 0f

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        player = Player(0.0f, 0.0f)
        player.loadTextures(context)

        enemies.add(Enemy(0.8f, 0.5f, type = EnemyType.TYPE1))
        enemies.add(Enemy(0.8f, -0.5f, type = EnemyType.TYPE1))
        enemies.forEach { it.loadTextures(context) }
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        this.width = width
        this.height = height
        player.setScreenDimensions(width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        player.draw(gl)
        enemies.forEach { it.draw(gl) }
        updateGame()
    }

    private fun updateGame() {
        player.move(joystickX, joystickY)
        player.update()
        enemies.forEach { it.update() }

        enemies.removeAll { it.isOutOfBound() }

        // Random generátor enemákov
        if (Math.random() < 0.01) {
            enemies.add(Enemy(0.8f, (Math.random() * 2.0f - 1.0f).toFloat(), type = EnemyType.TYPE1))
            enemies.last().loadTextures(context)
        }
    }

    fun getPlayer(): Player {
        return this.player
    }

    fun setEnemies(enemies: List<Enemy>) {
        this.enemies.clear()
        this.enemies.addAll(enemies)
    }

    fun setJoystickPosition(x: Float, y: Float) {
        joystickX = x
        joystickY = y
    }
}

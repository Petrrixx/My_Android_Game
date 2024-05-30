package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GameLoop(private val context: Context) : GLSurfaceView.Renderer {

    private lateinit var player: Player
    private val enemies = mutableListOf<Enemy>()

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(1f, 0f, 0f, 1f)
        player = Player(0.5f, 0.5f)
        player.loadTextures(context)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        update()
        draw(gl)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        player.setScreenDimensions(width, height)
    }

    private fun update() {
        player.update()
        if (Math.random() < 0.05) {
            val enemy = Enemy(1.0f, Math.random().toFloat())
            enemy.loadTextures(context)
            enemies.add(enemy)
        }
        for (enemy in enemies) {
            enemy.update()
        }
        enemies.removeAll { it.isOutOfBound() }
    }

    private fun draw(gl: GL10?) {
        player.draw(gl)
        for (enemy in enemies) {
            enemy.draw(gl)
        }
    }
}

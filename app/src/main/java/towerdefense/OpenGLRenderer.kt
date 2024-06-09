package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import android.opengl.Matrix


class OpenGLRenderer(private val context: Context) : GLSurfaceView.Renderer {
    private lateinit var player: Player
    private val enemies = mutableListOf<Enemy>()
    private var width = 0
    private var height = 0

    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val modelMatrix = FloatArray(16)
    private val mvpMatrix = FloatArray(16)

    private val program: Int = OpenGLUtils.createProgram()

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        player = Player(0.0f, 0.0f)
        player.loadTextures(context)

        enemies.add(Enemy(0.8f, 0.5f, type = EnemyType.TYPE1))
        enemies.add(Enemy(0.8f, -0.5f, type = EnemyType.TYPE1))
        enemies.forEach { it.loadTextures(context) }

        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        this.width = width
        this.height = height
        Matrix.frustumM(projectionMatrix, 0, -1f, 1f, -height.toFloat() / width, height.toFloat() / width, 3f, 7f)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

        GLES20.glUseProgram(program)

        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, -5f, 0f, 0f, 0f, 0f, 1f, 0f)

        // Draw player
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, player.x, player.y, 0f)
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, modelMatrix, 0)
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0)
        player.draw(program, mvpMatrix)

        // Draw enemies
        enemies.forEach { enemy ->
            Matrix.setIdentityM(modelMatrix, 0)
            Matrix.translateM(modelMatrix, 0, enemy.x, enemy.y, 0f)
            Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, modelMatrix, 0)
            Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0)
            enemy.draw(program, mvpMatrix)
        }

        updateGame()
    }

    private fun updateGame() {
        player.update()
        enemies.forEach { it.update() }
        enemies.removeAll { it.isOutOfBound() }

        if (Math.random() < 0.01) {
            enemies.add(Enemy(0.8f, (Math.random() * 2.0f - 1.0f).toFloat(), type = EnemyType.TYPE1))
            enemies.last().loadTextures(context)
        }
    }
}

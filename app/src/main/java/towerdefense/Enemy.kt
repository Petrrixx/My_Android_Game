package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLUtils
import android.graphics.BitmapFactory
import javax.microedition.khronos.opengles.GL10

class Enemy(private var x: Float, private var y: Float) {
    private var textureIds = IntArray(3) // 3 obrázky -> animácia
    private var currentFrame = 0
    private val frameCount = 3
    private var frameTime = 0L
    private val frameDuration = 100L // milisekundy za frame

    fun loadTextures(context: Context) {
        val resourceIds = intArrayOf(
            R.drawable.enemy_image1,
            R.drawable.enemy_image2,
            R.drawable.enemy_image3
        )

        GLES20.glGenTextures(textureIds.size, textureIds, 0)
        for (i in textureIds.indices) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[i])
            val bitmap = BitmapFactory.decodeResource(context.resources, resourceIds[i])
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
            bitmap.recycle()
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        }
    }

    fun draw(gl: GL10?) {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[currentFrame])
        //kód na vykreslenie nepriateľa
    }

    private fun updateFrame() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - frameTime >= frameDuration) {
            currentFrame = (currentFrame + 1) % frameCount
            frameTime = currentTime
        }
    }

    fun update() {
        x -= 0.01f // pohyb vľavo
        updateFrame()
    }

    fun isOutOfBound(): Boolean {
        return x < -1.0f
    }
}

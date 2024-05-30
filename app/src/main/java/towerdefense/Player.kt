package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLUtils
import android.graphics.BitmapFactory
import javax.microedition.khronos.opengles.GL10

class Player(private var x: Float, private var y: Float) {
    private var textureIds = IntArray(3) // 3 textury / frame
    private var currentFrame = 0
    private val frameCount = 3
    private var frameTime = 0L
    private val frameDuration = 100L // milliseconds/frame
    private var screenWidth = 0
    private var screenHeight = 0

    fun loadTextures(context: Context) {
        val resourceIds = intArrayOf(
            R.drawable.player_image1,
            R.drawable.player_image2,
            R.drawable.player_image3
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
        // draw
    }

    private fun updateFrame() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - frameTime >= frameDuration) {
            currentFrame = (currentFrame + 1) % frameCount
            frameTime = currentTime
        }
    }

    fun update() {
        //update logika
        updateFrame()
    }

    fun handleTouch(x: Float, y: Float) {
        // konvertovanie dimenzii obrazovky na OpenGL
        this.x = (x / screenWidth) * 2 - 1
        this.y = 1 - (y / screenHeight) * 2
    }

    fun setScreenDimensions(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height
    }
}

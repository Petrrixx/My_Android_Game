package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLUtils
import android.graphics.BitmapFactory
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10

class Enemy(private var x: Float, private var y: Float, private val type: EnemyType) {
    private var textureIds = IntArray(type.resourceIds.size)
    private var currentFrame = 0
    private val frameCount = type.resourceIds.size
    private var frameTime = 0L
    private val frameDuration = 100L // milisekundy za frame
    private val moveSpeed = 0.01f // Pohybová rýchlosť nepriateľa

    //buffere sú podľa YT tutorialu
    // Vertex data
    private val vertexBuffer: FloatBuffer = OpenGLUtils.createFloatBuffer(floatArrayOf(
        -0.1f,  0.1f, 0.0f,  // Top-left
        -0.1f, -0.1f, 0.0f,  // Bottom-left
        0.1f, -0.1f, 0.0f,  // Bottom-right
        0.1f,  0.1f, 0.0f   // Top-right
    ))

    // Texture coordinate data
    private val texCoordBuffer: FloatBuffer = OpenGLUtils.createFloatBuffer(floatArrayOf(
        0.0f, 0.0f,  // Top-left
        0.0f, 1.0f,  // Bottom-left
        1.0f, 1.0f,  // Bottom-right
        1.0f, 0.0f   // Top-right
    ))

    fun loadTextures(context: Context) {
        //popis a postup spracovania textúr je v objekte TextureUtils
        GLES20.glGenTextures(textureIds.size, textureIds, 0)
        for (i in textureIds.indices) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[i])
            val bitmap = BitmapFactory.decodeResource(context.resources, type.resourceIds[i])
            try {
                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                bitmap.recycle()
            }
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        }
    }

    fun draw(gl: GL10?) {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[currentFrame])

        GLES20.glEnableVertexAttribArray(0)
        GLES20.glVertexAttribPointer(0, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer)

        GLES20.glEnableVertexAttribArray(1)
        GLES20.glVertexAttribPointer(1, 2, GLES20.GL_FLOAT, false, 0, texCoordBuffer)

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4)

        GLES20.glDisableVertexAttribArray(0)
        GLES20.glDisableVertexAttribArray(1)
    }

    private fun updateFrame() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - frameTime >= frameDuration) {
            currentFrame = (currentFrame + 1) % frameCount
            frameTime = currentTime
        }
    }

    fun update() {
        x -= moveSpeed // pohyb vľavo
        updateFrame()
    }

    fun isOutOfBound(): Boolean {
        return x < -1.0f
    }

    fun release() {
        // Uvoľnenie OpenGL zdrojov, keď sa nepriateľ odstráni
        GLES20.glDeleteTextures(textureIds.size, textureIds, 0)
    }
}

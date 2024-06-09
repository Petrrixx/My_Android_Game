package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLUtils
import android.graphics.BitmapFactory
import java.nio.FloatBuffer

class Enemy(var x: Float, var y: Float, private val type: EnemyType) {
    private var textureIds = IntArray(type.resourceIds.size)
    private var currentFrame = 0
    private val frameCount = type.resourceIds.size
    private var frameTime = 0L
    private val frameDuration = 100L
    private val moveSpeed = 0.01f

    private val vertexBuffer: FloatBuffer = OpenGLUtils.createFloatBuffer(floatArrayOf(
        -0.1f,  0.1f, 0.0f,
        -0.1f, -0.1f, 0.0f,
        0.1f, -0.1f, 0.0f,
        0.1f,  0.1f, 0.0f
    ))

    private val texCoordBuffer: FloatBuffer = OpenGLUtils.createFloatBuffer(floatArrayOf(
        0.0f, 0.0f,
        0.0f, 1.0f,
        1.0f, 1.0f,
        1.0f, 0.0f
    ))

    fun loadTextures(context: Context) {
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

    fun draw(program: Int, mvpMatrix: FloatArray) {
        GLES20.glUseProgram(program)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[currentFrame])

        val positionHandle = GLES20.glGetAttribLocation(program, "vPosition")
        val texCoordHandle = GLES20.glGetAttribLocation(program, "aTexCoord")
        val mvpMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix")

        GLES20.glEnableVertexAttribArray(positionHandle)
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer)

        GLES20.glEnableVertexAttribArray(texCoordHandle)
        GLES20.glVertexAttribPointer(texCoordHandle, 2, GLES20.GL_FLOAT, false, 0, texCoordBuffer)

        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0)

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4)

        GLES20.glDisableVertexAttribArray(positionHandle)
        GLES20.glDisableVertexAttribArray(texCoordHandle)
    }

    fun update() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - frameTime >= frameDuration) {
            currentFrame = (currentFrame + 1) % frameCount
            frameTime = currentTime
        }
        x -= moveSpeed
    }

    fun isOutOfBound(): Boolean {
        return x < -1.0f
    }
}

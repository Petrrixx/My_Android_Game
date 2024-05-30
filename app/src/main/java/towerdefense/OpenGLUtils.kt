package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder

object OpenGLUtils {
    private const val vertexShaderCode =
        "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "attribute vec2 aTexCoord;" +
                "varying vec2 vTexCoord;" +
                "void main() {" +
                "  gl_Position = uMVPMatrix * vPosition;" +
                "  vTexCoord = aTexCoord;" +
                "}"

    private const val fragmentShaderCode =
        "precision mediump float;" +
                "uniform sampler2D sTexture;" +
                "varying vec2 vTexCoord;" +
                "void main() {" +
                "  gl_FragColor = texture2D(sTexture, vTexCoord);" +
                "}"

    fun createProgram(): Int {
        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
        return GLES20.glCreateProgram().apply {
            GLES20.glAttachShader(this, vertexShader)
            GLES20.glAttachShader(this, fragmentShader)
            GLES20.glLinkProgram(this)
        }
    }

    private fun loadShader(type: Int, shaderCode: String): Int {
        return GLES20.glCreateShader(type).apply {
            GLES20.glShaderSource(this, shaderCode)
            GLES20.glCompileShader(this)
        }
    }

    fun createQuadVertexBuffer(): Int {
        val vertices = floatArrayOf(
            -0.1f, 0.1f, 0.0f,
            -0.1f, -0.1f, 0.0f,
            0.1f, 0.1f, 0.0f,
            0.1f, -0.1f, 0.0f
        )
        val buffer = ByteBuffer.allocateDirect(vertices.size * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
        buffer.put(vertices).position(0)

        val buffers = IntArray(1)
        GLES20.glGenBuffers(1, buffers, 0)
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0])
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, buffer.capacity() * 4, buffer, GLES20.GL_STATIC_DRAW)
        return buffers[0]
    }

    fun createQuadUvBuffer(): Int {
        val uvs = floatArrayOf(
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f
        )
        val buffer = ByteBuffer.allocateDirect(uvs.size * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
        buffer.put(uvs).position(0)

        val buffers = IntArray(1)
        GLES20.glGenBuffers(1, buffers, 0)
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0])
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, buffer.capacity() * 4, buffer, GLES20.GL_STATIC_DRAW)
        return buffers[0]
    }
}

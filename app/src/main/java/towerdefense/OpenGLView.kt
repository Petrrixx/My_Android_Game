package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

class OpenGLView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : GLSurfaceView(context, attrs) {
    private val renderer: GLRenderer

    init {
        setEGLContextClientVersion(2)
        renderer = GLRenderer(context)
        setRenderer(renderer)
        renderMode = RENDERMODE_CONTINUOUSLY
    }
}



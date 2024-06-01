package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

// inicializácia zobrazovacieho okna pomocou predom nastaveného vlastného rendrovacieho enginu
class OpenGLView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : GLSurfaceView(context, attrs) {
    private val renderer: OpenGLRenderer
    init {
        setEGLContextClientVersion(2)
        renderer = OpenGLRenderer(context)
        setRenderer(renderer)
        renderMode = RENDERMODE_CONTINUOUSLY
    }

    fun getRenderer(): OpenGLRenderer {
        return renderer
    }
}

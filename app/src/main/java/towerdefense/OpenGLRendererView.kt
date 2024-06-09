package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Context
import android.opengl.GLSurfaceView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun OpenGLRendererView(context: Context) {
    AndroidView(
        factory = {
            GLSurfaceView(context).apply {
                setEGLContextClientVersion(2)
                setRenderer(OpenGLRenderer(context))
                renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
            }
        },
        update = {}
    )
}

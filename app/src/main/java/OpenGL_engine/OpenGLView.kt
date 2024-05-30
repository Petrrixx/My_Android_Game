package OpenGL_engine

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

class OpenGLView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,

) : GLSurfaceView(context, attributes) {

    private val renderer : GLRenderer = GLRenderer();

    init {
        //OpenGL ES 3.2 použijem
        setEGLContextClientVersion(3);
        setPreserveEGLContextOnPause(true);

        setRenderer(renderer);
    }



}


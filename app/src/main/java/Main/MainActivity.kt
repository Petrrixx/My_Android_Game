package Main

import OpenGL_engine.OpenGLView
import android.os.Bundle
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var openGLView : OpenGLView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openGLView = OpenGLView(this);
        setContentView(openGLView);
    }
}

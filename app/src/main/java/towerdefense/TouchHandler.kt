package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.view.MotionEvent
import android.view.View

// Je to presne to, ako sa tá trieda volá
class TouchHandler(private val renderer: OpenGLRenderer) : View.OnTouchListener {
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    renderer.getPlayer().handleTouch(event.x, event.y)
                }
            }
        }
        return true
    }
}


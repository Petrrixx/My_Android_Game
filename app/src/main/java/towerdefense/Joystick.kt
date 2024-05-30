package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class Joystick(context: Context, private val centerX: Float, private val centerY: Float, private val baseRadius: Float, private val hatRadius: Float) : View(context) {
    private var joystickX: Float = centerX
    private var joystickY: Float = centerY
    private var paint: Paint = Paint()

    init {
        paint.color = 0xFF00FF00.toInt() // zelená
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(centerX, centerY, baseRadius, paint)
        canvas.drawCircle(joystickX, joystickY, hatRadius, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action != MotionEvent.ACTION_UP) {
            joystickX = event.x
            joystickY = event.y
        } else {
            joystickX = centerX
            joystickY = centerY
        }
        invalidate()
        return true
    }
}

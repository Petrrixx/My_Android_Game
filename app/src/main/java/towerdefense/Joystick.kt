package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class Joystick @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var baseRadius: Float = 150f
    private var hatRadius: Float = 75f
    private var joystickX: Float = centerX
    private var joystickY: Float = centerY
    private var paint: Paint = Paint()

    init {
        paint.color = 0xFF00FF00.toInt() // green
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(centerX, centerY, baseRadius, paint)
        canvas.drawCircle(joystickX, joystickY, hatRadius, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val relativeX = event.x
        val relativeY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                joystickX = relativeX
                joystickY = relativeY
            }
            MotionEvent.ACTION_UP -> {
                joystickX = centerX
                joystickY = centerY
            }
        }
        invalidate()
        return true
    }

    fun setCenter(centerX: Float, centerY: Float) {
        this.centerX = centerX
        this.centerY = centerY
        joystickX = centerX
        joystickY = centerY
        invalidate()
    }

    fun setRadius(baseRadius: Float, hatRadius: Float) {
        this.baseRadius = baseRadius
        this.hatRadius = hatRadius
        invalidate()
    }
}

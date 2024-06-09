package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun Joystick(
    onDirectionChange: (
        up: Boolean, down: Boolean, left: Boolean, right: Boolean,
        upLeft: Boolean, upRight: Boolean, downLeft: Boolean, downRight: Boolean
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    val direction = remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = modifier
            .size(150.dp)
            .background(Color.Transparent)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    direction.value = dragAmount
                    val (x, y) = dragAmount
                    val up = y < -20
                    val down = y > 20
                    val left = x < -20
                    val right = x > 20
                    val upLeft = up && left
                    val upRight = up && right
                    val downLeft = down && left
                    val downRight = down && right
                    onDirectionChange(up, down, left, right, upLeft, upRight, downLeft, downRight)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(Color.Green, radius = size.minDimension / 2)
            val (x, y) = direction.value
            drawCircle(
                Color.Red,
                radius = size.minDimension / 4,
                center = Offset(size.width / 2 + x, size.height / 2 + y)
            )
        }
    }
}

package com.jann.ai.feature.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.jann.ai.core.ui.theme.JannColors
import kotlin.math.cos
import kotlin.math.sin

/** Animated JARVIS-style core. One infinite transition drives everything (battery-friendly). */
@Composable
fun AiOrb(state: OrbState, modifier: Modifier = Modifier) {
    val color by animateColorAsState(state.color, tween(500), label = "orbColor")
    val energy by animateFloatAsState(state.energy, tween(600), label = "orbEnergy")

    val t = rememberInfiniteTransition(label = "orb")
    val rot by t.animateFloat(
        0f, 360f,
        infiniteRepeatable(tween(12_000, easing = LinearEasing)), label = "rot"
    )
    val pulse by t.animateFloat(
        0f, 1f,
        infiniteRepeatable(tween(1_300, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "pulse"
    )

    Canvas(modifier.size(280.dp)) {
        val c = center
        val r = size.minDimension / 2f
        val scale = 1f + energy * 0.10f * pulse
        val spin = 1f + energy * 2f

        drawCircle(
            Brush.radialGradient(listOf(color.copy(alpha = 0.30f + 0.2f * energy), Color.Transparent), c, r),
            radius = r
        )
        val cr = r * 0.52f * scale
        drawCircle(
            Brush.radialGradient(
                listOf(Color.White.copy(alpha = 0.95f), color, JannColors.Violet.copy(alpha = 0.7f), Color.Transparent),
                c, cr * 1.25f
            ),
            radius = cr * 1.25f
        )
        rotate(rot * spin, c) {
            drawCircle(
                color.copy(alpha = 0.7f), radius = r * 0.78f,
                style = Stroke(3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(28f, 22f)))
            )
        }
        rotate(-rot * spin * 1.4f, c) {
            drawArc(
                JannColors.Violet, 0f, 110f, false,
                topLeft = Offset(c.x - r * 0.9f, c.y - r * 0.9f),
                size = Size(r * 1.8f, r * 1.8f),
                style = Stroke(5f, cap = StrokeCap.Round)
            )
        }
        repeat(20) { i ->
            val a = Math.toRadians((rot * (0.4 + (i % 3) * 0.3) * spin + i * 18.0))
            val dist = r * (0.62f + 0.32f * ((i * 37) % 10) / 10f)
            drawCircle(
                Color.White.copy(alpha = 0.35f + 0.4f * pulse),
                radius = 2f + (i % 3),
                center = Offset(c.x + cos(a).toFloat() * dist, c.y + sin(a).toFloat() * dist)
            )
        }
    }
}

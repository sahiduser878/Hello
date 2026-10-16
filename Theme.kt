package com.jann.ai.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object JannColors {
    val Black = Color(0xFF05070D)
    val Navy = Color(0xFF0A1128)
    val Electric = Color(0xFF2EC4FF)
    val Violet = Color(0xFF8B5CFF)
    val Danger = Color(0xFFFF4D6D)
    val Glass = Color(0x1AFFFFFF)
    val GlassBorder = Color(0x1FFFFFFF)
}

private val Scheme = darkColorScheme(
    primary = JannColors.Electric,
    secondary = JannColors.Violet,
    background = JannColors.Black,
    surface = JannColors.Navy,
    onPrimary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    error = JannColors.Danger,
)

@Composable
fun JannTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Scheme, content = content)
}

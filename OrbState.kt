package com.jann.ai.feature.home

import androidx.compose.ui.graphics.Color
import com.jann.ai.core.ui.theme.JannColors

enum class OrbState(val label: String, val color: Color, val energy: Float) {
    Idle("Idle", JannColors.Electric, 0.25f),
    Listening("Listening…", Color(0xFF35F0C5), 0.7f),
    Thinking("Thinking…", JannColors.Violet, 1.0f),
    Speaking("Speaking…", Color(0xFF5CA8FF), 0.85f),
    Error("Something went wrong", JannColors.Danger, 0.4f),
}

package com.finalnight.game.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val NightColors = darkColorScheme(
    primary = Color(0xFFE53945),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFFFFB4A9),
    background = Color(0xFF08090D),
    surface = Color(0xFF15171D),
    onBackground = Color(0xFFF2F2F2),
    onSurface = Color(0xFFF2F2F2),
    error = Color(0xFFFF6B6B)
)

@Composable
fun GameTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = NightColors,
        content = content
    )
}

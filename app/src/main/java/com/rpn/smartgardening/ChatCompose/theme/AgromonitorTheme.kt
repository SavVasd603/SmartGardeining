package com.ahmedapps.geminichatbot.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Green,
    onPrimary = White,
    secondary = Teal200,
    // Define other colors if needed
)

private val DarkColors = darkColorScheme(
    primary = Green,
    onPrimary = White,
    secondary = Teal200,
    // Define other colors if needed
)

@Composable
fun AgromonitorTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}

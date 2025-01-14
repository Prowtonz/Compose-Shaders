package com.nicholas.composeshaders.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

private val DarkColorPalette = darkColorScheme(
    primary = Purple200,
    tertiary = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColorScheme(
    primary = Purple500,
    tertiary = Purple700,
    secondary = Teal200,
    background = PrimaryTextColor

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

private val statusBarColorChannel = Channel<Color>()
private val statusBarColorFlow = statusBarColorChannel.receiveAsFlow()

suspend fun setStatusBarColor(color: Color) {
    statusBarColorChannel.send(color)
}

@Composable
fun ComposeShadersTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val view = LocalView.current
    val statusBarColor by statusBarColorFlow.collectAsStateWithLifecycle(initialValue = colors.primary)
    LaunchedEffect(statusBarColor) {
        val activity = view.context as Activity
        activity.window.statusBarColor = statusBarColor.toArgb()
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
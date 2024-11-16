package com.nicholas.composeshaders.ui.screens.shaderdemo

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Target
import com.nicholas.composeshaders.services.shaders.Shader
import com.nicholas.composeshaders.ui.opengl.GLShader
import com.nicholas.composeshaders.ui.opengl.ShaderRenderer
import com.nicholas.composeshaders.ui.screens.shaderdemo.models.ButtonColorHolder
import com.nicholas.composeshaders.ui.theme.PrimaryTextColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val BOTTOM_SHEET_HEIGHT = 240

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShaderDemo(shader: Shader, modifier: Modifier = Modifier) {
    val renderer = remember(::ShaderRenderer)
    val coroutineScope = rememberCoroutineScope()
    val velocityThreshold: Float
    val bottomSheetHeightInPixels = with(LocalDensity.current) {
        velocityThreshold = 125.dp.toPx()
        BOTTOM_SHEET_HEIGHT.dp.toPx()
    }
    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            initialValue = 0,
            snapAnimationSpec = tween(),
            decayAnimationSpec = exponentialDecay(),
            velocityThreshold = { velocityThreshold },
            positionalThreshold = { it / 2f },
            anchors = DraggableAnchors {
                0 at bottomSheetHeightInPixels
                1 at 0f
            }
        )
    }
    val isBottomSheetVisible by remember {
        derivedStateOf { anchoredDraggableState.currentValue == 1 }
    }
    var buttonColorPair by remember {
        mutableStateOf(
            ButtonColorHolder(android.graphics.Color.BLACK, android.graphics.Color.WHITE)
        )
    }
    LaunchedEffect(shader) {
        renderer.setShaders(shader.fragmentShader, shader.vertexShader)
        delay(500)
        renderer.getButtonColors { buttonColorPair = it }
    }
    Box(modifier.anchoredDraggable(anchoredDraggableState, Orientation.Vertical)) {
        GLShader(renderer, Modifier.fillMaxSize())
        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .offset { IntOffset(0, anchoredDraggableState.offset.toInt()) }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black.copy(0.9f))
                        )
                    )
                    .clickable {
                        coroutineScope.launch {
                            anchoredDraggableState.animateTo(if(isBottomSheetVisible) 0 else 1)
                        }
                    }
            ) {
                SwipeIcon(
                    BOTTOM_SHEET_HEIGHT.dp,
                    isBottomSheetVisible,
                    anchoredDraggableState,
                    Modifier.padding(top = 60.dp)
                )
                Text(
                    if(isBottomSheetVisible) "Hide text" else "Tap here!",
                    color = PrimaryTextColor,
                    modifier = Modifier.padding(top = 14.dp, bottom = 10.dp)
                )
            }
            ShaderBottomSheet(
                shader,
                buttonColorPair,
                Modifier.requiredHeight(BOTTOM_SHEET_HEIGHT.dp)
            )
        }
    }
}

private fun ShaderRenderer.getButtonColors(callback: (ButtonColorHolder) -> Unit) {
    setSampleShaderImageCallback {
        val palette = Palette.Builder(it)
            .addTarget(Target.VIBRANT)
            .maximumColorCount(6)
            .generate()
        var textColor = android.graphics.Color.WHITE
        var backgroundColor = android.graphics.Color.BLACK
        (palette.lightVibrantSwatch ?: palette.lightMutedSwatch ?: palette.dominantSwatch
        ?: palette.vibrantSwatch ?: palette.darkVibrantSwatch)?.let { swatch ->
            textColor = swatch.bodyTextColor
            backgroundColor = swatch.rgb
        }
        callback(ButtonColorHolder(backgroundColor, textColor))
    }
}
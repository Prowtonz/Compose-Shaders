package com.nicholas.composeshaders.ui.screens.shaderdemo

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.nicholas.composeshaders.ui.theme.PrimaryTextColor
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import com.nicholas.composeshaders.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeIcon(
    bottomSheetHeight: Dp,
    isBottomSheetVisible: Boolean,
    swipeOffset: AnchoredDraggableState<Int>,
    modifier: Modifier = Modifier
) {
    val repeatingAnimation = rememberInfiniteTransition(label = "")
    val bottomSheetHeightInPixels = with(LocalDensity.current) { bottomSheetHeight.toPx() }
    val offset by repeatingAnimation.animateFloat(
        label = "",
        initialValue = 0f,
        targetValue = -20f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            RepeatMode.Reverse
        )
    )
    val yOffset = if(isBottomSheetVisible) 0 else offset.absoluteValue.roundToInt()
    Icon(
        painter = painterResource(R.drawable.ic_double_down),
        contentDescription = null,
        tint = PrimaryTextColor,
        modifier = modifier
            .size(16.dp)
            .offset { IntOffset(0, yOffset) }
            .rotate(map(swipeOffset.offset, bottomSheetHeightInPixels))
    )








}

private fun map(value: Float, startRangeMin: Float) =
    (value - startRangeMin) / -startRangeMin * -180f + 180f
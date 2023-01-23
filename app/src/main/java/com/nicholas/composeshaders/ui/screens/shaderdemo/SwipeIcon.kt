package com.nicholas.composeshaders.ui.screens.shaderdemo

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.SwipeableState
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeIcon(
    bottomSheetHeight: Dp,
    isBottomSheetVisible: Boolean,
    swipeOffset: SwipeableState<Int>,
    modifier: Modifier = Modifier
) {
    val repeatingAnimation = rememberInfiniteTransition()
    val bottomSheetHeightInPixels = with(LocalDensity.current) { bottomSheetHeight.toPx() }
    val offset by repeatingAnimation.animateFloat(
        0f,
        -20f,
        infiniteRepeatable(
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
            .rotate(map(swipeOffset.offset.value, bottomSheetHeightInPixels))
    )








}

private fun map(value: Float, startRangeMin: Float) =
    (value - startRangeMin) / -startRangeMin * -180f + 180f
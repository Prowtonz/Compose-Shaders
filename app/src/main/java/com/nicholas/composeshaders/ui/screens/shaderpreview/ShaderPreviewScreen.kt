package com.nicholas.composeshaders.ui.screens.shaderpreview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.material.math.MathUtils
import com.nicholas.composeshaders.services.shaders.Shader
import com.nicholas.composeshaders.services.shaders.getShaderList
import com.nicholas.composeshaders.ui.theme.ComposeShadersTheme
import com.nicholas.composeshaders.ui.theme.PrimaryTextColor
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ShaderPreviewScreen(onShaderSelected: (Shader) -> Unit) {
    val context = LocalContext.current
    val backgroundColor = MaterialTheme.colors.background
    val pagerState = rememberPagerState()
    val shaders = remember { getShaderList(context) }
    val systemUIController = rememberSystemUiController()
    LaunchedEffect(true) { systemUIController.setStatusBarColor(backgroundColor) }
    Box(contentAlignment = Alignment.CenterEnd) {
        VerticalPager(
            count = shaders.size,
            state = pagerState,
            itemSpacing = 200.dp,
            key = { shaders[it].title }
        ) {
            val shader = remember { shaders[it] }
            PreviewCard(shader)
            Surface(
                color = MaterialTheme.colors.background.copy(1f),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .clickable { onShaderSelected(shader) }
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(it).absoluteValue
                        alpha = MathUtils.lerp(1f, 0f, 1f - pageOffset.coerceIn(0f, 1f))
                    }
            ) {}
        }
        VerticalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.padding(20.dp),
            indicatorShape = RoundedCornerShape(3.dp),
            indicatorWidth = 5.dp,
            indicatorHeight = 8.dp,
            activeColor = PrimaryTextColor,
            inactiveColor = PrimaryTextColor.copy(alpha = 0.4f)
        )
    }
}
package com.nicholas.composeshaders.ui.screens.shaderpreview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.util.lerp
import com.nicholas.composeshaders.services.shaders.Shader
import com.nicholas.composeshaders.services.shaders.getShaderList
import com.nicholas.composeshaders.ui.theme.PrimaryTextColor
import com.nicholas.composeshaders.ui.theme.setStatusBarColor
import kotlin.math.absoluteValue

@Composable
fun ShaderPreviewScreen(onShaderSelected: (Shader) -> Unit) {
    val context = LocalContext.current
    val backgroundColor = MaterialTheme.colorScheme.background
    val shaders = remember { getShaderList(context) }
    val pagerState = rememberPagerState { shaders.size }
    LaunchedEffect(true) { setStatusBarColor(backgroundColor) }
    Box(contentAlignment = Alignment.CenterEnd) {
        VerticalPager(
            state = pagerState,
            pageSpacing = 200.dp,
            key = { shaders[it].title }
        ) {
            val shader = remember { shaders[it] }
            Box {
                PreviewCard(shader)
                Surface(
                    color = MaterialTheme.colorScheme.background.copy(1f),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding()
                        .clickable { onShaderSelected(shader) }
                        .graphicsLayer {
                            val pageOffset = ((pagerState.currentPage - it) + pagerState.currentPageOffsetFraction).absoluteValue
                            alpha = lerp(1f, 0f, 1f - pageOffset.coerceIn(0f, 1f))
                        }
                ) {}
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(end = 20.dp)
        ) {
            shaders.fastForEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .size(width = 5.dp, height = 8.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(if(pagerState.currentPage == index) PrimaryTextColor else PrimaryTextColor.copy(alpha = 0.4f))
                )
            }
        }
    }
}
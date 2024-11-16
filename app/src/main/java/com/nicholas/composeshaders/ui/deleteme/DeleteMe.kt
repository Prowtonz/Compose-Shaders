package com.nicholas.composeshaders.ui.deleteme

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.nicholas.composeshaders.ui.theme.ComposeShadersTheme

@Composable
fun DeleteMe() {
    Box(modifier = Modifier) {
        Text(
            "Compose Shaders",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 120.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF121212),
            lineHeight = 140.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .graphicsLayer {
                    translationY = -80f
                }
        )
        Text(
            "by Nicholas Torres",
            fontSize = 50.sp,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF5B5B5B),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .graphicsLayer {
                    translationY = 10f
                }
        )
    }
}

@Composable
@Preview(
    backgroundColor = 0xFFEBDBC1,
    showBackground = true,
    widthDp = 872,
    heightDp = 332
)
fun DeleteMePreview() {
    ComposeShadersTheme {
        DeleteMe()
    }
}
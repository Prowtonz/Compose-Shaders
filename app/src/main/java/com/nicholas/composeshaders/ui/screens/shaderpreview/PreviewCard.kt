package com.nicholas.composeshaders.ui.screens.shaderpreview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nicholas.composeshaders.services.shaders.Shader
import com.nicholas.composeshaders.ui.opengl.GLShader
import com.nicholas.composeshaders.ui.opengl.ShaderRenderer
import com.nicholas.composeshaders.ui.theme.PrimaryTextColor

@Composable
fun PreviewCard(shader: Shader) {
    val shaderRenderer = remember {
        ShaderRenderer().apply { setShaders(shader.fragmentShader, shader.vertexShader) }
    }
    Card(modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)) {
        Box(contentAlignment = Alignment.BottomCenter) {
            GLShader(renderer = shaderRenderer)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            endY = 400f
                        )
                    )
            ) {
                Text(
                    shader.title,
                    color = PrimaryTextColor,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp, 76.dp, 16.dp, 16.dp)
                )
            }
        }
    }
}
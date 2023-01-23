package com.nicholas.composeshaders.ui.screens.shaderdemo

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicholas.composeshaders.services.shaders.Shader
import com.nicholas.composeshaders.ui.screens.shaderdemo.models.ButtonColorHolder

@Composable
fun ShaderBottomSheet(
    shader: Shader,
    buttonColorHolder: ButtonColorHolder,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.Black.copy(0.9f),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                shader.title,
                color = Color.White,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Medium,
                letterSpacing = 4.sp,
                modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 2.dp)
            )
            Text(
                shader.description,
                color = Color.White,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 20.dp)
            )
            AnimatedButton(
                shader.id,
                buttonColorHolder,
                Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
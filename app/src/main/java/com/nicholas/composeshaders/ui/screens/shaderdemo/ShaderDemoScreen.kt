package com.nicholas.composeshaders.ui.screens.shaderdemo

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nicholas.composeshaders.services.shaders.getShaderList


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShaderDemoScreen(selectedShaderIndex: Int, onBackPressed: () -> Unit) {
    val context = LocalContext.current
    val shaders = remember { getShaderList(context) }
    var shaderIndex by remember { mutableIntStateOf(selectedShaderIndex) }
    Scaffold {
        Box(Modifier.fillMaxSize()) {
            ShaderDemo(shaders[shaderIndex], Modifier.fillMaxSize())
            DemoPagination(
                shaderIndex = shaderIndex,
                count = shaders.size,
                modifier = Modifier.align(Alignment.Center)
            ) { shaderIndex = it }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Black.copy(0.3f), Color.Transparent)
                        )
                    )
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .clickable(onClick = onBackPressed)
                        .padding(top = 20.dp, start = 20.dp)
                        .size(26.dp)
                )
            }
        }
    }
}
package com.nicholas.composeshaders.ui.screens.shaderdemo

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nicholas.composeshaders.services.preferencemanager.PreferenceManager
import com.nicholas.composeshaders.ui.screens.shaderdemo.models.ButtonColorHolder
import com.nicholas.composeshaders.ui.screens.shaderdemo.models.ButtonState
import com.nicholas.composeshaders.ui.theme.PrimaryTextColor
import com.nicholas.composeshaders.services.wallpaperservice.ShadersWallpaperService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

@Composable
fun AnimatedButton(
    shaderIndex: Int,
    buttonColors: ButtonColorHolder,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val systemUIController = rememberSystemUiController()
    val preferenceManager = remember { PreferenceManager(context) }
    val isWallpaperServiceRunning = ShadersWallpaperService.isRunning(context)
    val selectedShader by preferenceManager.getSelectedShader().collectAsState(-1)
    var buttonState by remember {
        mutableStateOf(ButtonState())
    }
    val buttonBGColor by animateColorAsState(
        Color(buttonColors.backgroundColor),
        tween(500)
    )
    val buttonTextColor by animateColorAsState(
        Color(buttonColors.textColor),
        tween(500)
    )
    val buttonAlphaAndWidth by animateFloatAsState(
        if(buttonState.showLoader) 0f else 1f,
        tween(600, easing = FastOutSlowInEasing)
    )
    DisposableEffect(true) { onDispose(preferenceManager::release) }
    LaunchedEffect(buttonBGColor) {
        val shouldUseDarkIcons = buttonBGColor.luminance() >= 0.5f
        systemUIController.setStatusBarColor(buttonBGColor, shouldUseDarkIcons)
    }
    LaunchedEffect(shaderIndex, selectedShader, isWallpaperServiceRunning) {
        val showSuccessMessage = selectedShader == shaderIndex && isWallpaperServiceRunning
        buttonState = ButtonState(showSuccessMessage = showSuccessMessage)
    }
    Box(
        modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            border = BorderStroke(1.dp, PrimaryTextColor),
            modifier = Modifier
                .padding(8.dp)
                .heightIn(48.dp)
                .alpha(buttonAlphaAndWidth)
                .fillMaxWidth(buttonAlphaAndWidth),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonBGColor.copy(
                    if(buttonState.showSuccessMessage) 0.8f else 1f
                )
            ),
            onClick = {
                if(!buttonState.showSuccessMessage) coroutineScope.launch {
                    if(isWallpaperServiceRunning) {
                        buttonState = ButtonState(true)
                        delay(500)
                        preferenceManager.setSelectedShader(shaderIndex)
                    } else {
                        preferenceManager.setSelectedShader(shaderIndex)
                        context.openLiveWallpaperChooser()
                    }
                }
            }
        ) {
            if(!buttonState.showLoader && !buttonState.showSuccessMessage) Text(
                "Set As Live Wallpaper",
                color = buttonTextColor,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            ) else if(buttonState.showSuccessMessage) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = null,
                    tint = buttonTextColor.copy(0.7f),
                    modifier = Modifier
                        .padding(top = 4.dp, end = 6.dp)
                        .size(20.dp)
                )
                Text(
                    "Wallpaper set!",
                    color = buttonTextColor,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        AnimatedVisibility(
            visible = buttonState.showLoader,
            enter = fadeIn(tween(800)),
            exit = fadeOut(tween(300))
        ) {
            CircularProgressIndicator(color = PrimaryTextColor)
        }
    }
}

private fun Context.openLiveWallpaperChooser() {
    try {
        val intent = Intent()
        intent.action = WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER
        intent.putExtra(
            WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
            ComponentName(this, ShadersWallpaperService::class.java)
        )
        startActivity(intent)
    } catch(err: IOException) {
        err.printStackTrace()
    }
}
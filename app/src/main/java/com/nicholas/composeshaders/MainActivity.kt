package com.nicholas.composeshaders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.nicholas.composeshaders.ui.ComposeShadersApp
import com.nicholas.composeshaders.ui.theme.ComposeShadersTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            ComposeShadersTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ComposeShadersApp()
                }
            }
        }
    }

}
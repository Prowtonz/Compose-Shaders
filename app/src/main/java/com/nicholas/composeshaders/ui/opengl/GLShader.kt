package com.nicholas.composeshaders.ui.opengl

import android.opengl.GLSurfaceView.DEBUG_CHECK_GL_ERROR
import android.opengl.GLSurfaceView.DEBUG_LOG_GL_CALLS
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun GLShader(renderer: ShaderRenderer, modifier: Modifier = Modifier) {
    var view: ShaderGLSurfaceView? = remember { null }
    val lifecycleState = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycleState) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if(event == Lifecycle.Event.ON_PAUSE) {
                view?.onPause()
                renderer.onPause()
            } else if(event == Lifecycle.Event.ON_RESUME) {
                view?.onResume()
                renderer.onResume()
            }
        }
        lifecycleState.addObserver(lifecycleObserver)
        onDispose {
            lifecycleState.removeObserver(lifecycleObserver)
            view?.onPause()
            view = null
        }
    }
    AndroidView(
        modifier = modifier,
        factory = ::ShaderGLSurfaceView
    ) {
        view = it
        it.debugFlags = DEBUG_CHECK_GL_ERROR or DEBUG_LOG_GL_CALLS
        it.setRenderer(renderer)
    }
}
package com.nicholas.composeshaders.services.wallpaperservice

import android.app.WallpaperColors
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.nicholas.composeshaders.services.preferencemanager.PreferenceManager
import com.nicholas.composeshaders.services.shaders.Shader
import com.nicholas.composeshaders.services.shaders.getShaderList
import com.nicholas.composeshaders.ui.opengl.ShaderRenderer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ShadersWallpaperService : WallpaperService() {

    companion object {

        fun isRunning(context: Context): Boolean {
            val wallpaperInfo = WallpaperManager.getInstance(context).wallpaperInfo
            return wallpaperInfo != null && wallpaperInfo.packageName == context.packageName
        }

    }

    override fun onCreateEngine() = WallpaperEngine()

    inner class WallpaperEngine : WallpaperService.Engine(), LifecycleOwner {

        private var selectedShader = null as Shader?
        private var renderer = null as ShaderRenderer?
        private lateinit var preferenceManager: PreferenceManager
        private val lifecycleRegistry = LifecycleRegistry(this)
        private var glSurfaceView = null as LiveWallpaperGLSurfaceView?

        private fun setSurfaceView(holder: SurfaceHolder) {
            val surfaceView = object : LiveWallpaperGLSurfaceView(applicationContext) {
                override fun getHolder() = holder
            }
            glSurfaceView = surfaceView
            val shaderRenderer = ShaderRenderer()
            renderer = shaderRenderer
            selectedShader?.let { shaderRenderer.setShaders(it.fragmentShader, it.vertexShader) }
            surfaceView.setRenderer(shaderRenderer)
        }

        override fun getLifecycle() = lifecycleRegistry

        @RequiresApi(Build.VERSION_CODES.O_MR1)
        override fun onComputeColors(): WallpaperColors {
            val color = Color.valueOf(Color.BLACK)
            return WallpaperColors(color, color, color)
        }

        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
            preferenceManager = PreferenceManager(applicationContext)
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    preferenceManager
                        .getSelectedShader()
                        .filter { it != -1 }
                        .onEach {
                            val shaders = getShaderList(applicationContext)
                            val newShader = shaders[it]
                            selectedShader = newShader
                            if(glSurfaceView == null) setSurfaceView(surfaceHolder)
                            renderer?.setShaders(newShader.fragmentShader, newShader.vertexShader)
                        }
                        .collect()
                }
            }
            setSurfaceView(surfaceHolder)
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        }

        override fun onDestroy() {
            super.onDestroy()
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            glSurfaceView?.onPause()
            glSurfaceView?.onDestroy()
            glSurfaceView = null
            renderer = null
            preferenceManager.release()
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
            if(!isPreview) return
            stopSelf()
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            glSurfaceView?.onPause()
            glSurfaceView = null
            renderer = null
            preferenceManager.release()
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            if(visible) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
                glSurfaceView?.onResume()
            } else {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
                glSurfaceView?.onPause()
            }
        }

    }

}
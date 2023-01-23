package com.nicholas.composeshaders.services.wallpaperservice

import android.content.Context
import android.opengl.GLSurfaceView

open class LiveWallpaperGLSurfaceView(context: Context) : GLSurfaceView(context) {

    init {
        this.setEGLContextClientVersion(2)
        preserveEGLContextOnPause = true
    }

    private var hasSetRenderer = false

    override fun onResume() {
        super.onResume()
        renderMode = RENDERMODE_CONTINUOUSLY
    }

    override fun onPause() {
        super.onPause()
        renderMode = RENDERMODE_WHEN_DIRTY
    }

    override fun setRenderer(renderer: Renderer) {
        if(hasSetRenderer) return
        super.setRenderer(renderer)
        hasSetRenderer = true
    }

    fun onDestroy() = onDetachedFromWindow()

}
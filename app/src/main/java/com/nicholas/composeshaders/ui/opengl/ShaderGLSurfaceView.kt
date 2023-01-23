package com.nicholas.composeshaders.ui.opengl

import android.content.Context
import android.opengl.GLSurfaceView

class ShaderGLSurfaceView(context: Context) : GLSurfaceView(context) {

    init {
        setEGLContextClientVersion(2)
        preserveEGLContextOnPause = true
    }

    private var hasRenderer = false

    override fun setRenderer(renderer: Renderer) {
        if(hasRenderer) return
        super.setRenderer(renderer)
        hasRenderer = true
    }

}
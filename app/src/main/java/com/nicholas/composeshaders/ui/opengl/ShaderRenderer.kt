package com.nicholas.composeshaders.ui.opengl

import android.graphics.Bitmap
import android.opengl.GLES10.GL_PERSPECTIVE_CORRECTION_HINT
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.atomic.AtomicBoolean
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class ShaderRenderer: GLSurfaceView.Renderer {

    private var frameCount = 0f
    private var surfaceWidth = 0
    private var surfaceHeight = 0
    private var programId: Int? = null
    private var timeUniformLocation: Int? = null
    private var positionAttributeLocation: Int? = null
    private var resolutionUniformLocation: Int? = null
    private val shouldPlay = AtomicBoolean(false)
    private val isProgramChanged = AtomicBoolean(false)
    private var sampleShaderBitmapCallback = null as ((Bitmap) -> Unit)?
    private lateinit var fragmentShader: String
    private lateinit var vertexShader: String

    private val quadVertices by lazy { floatArrayOf(-1f, 1f, 1f, 1f, -1f, -1f, 1f, -1f) }

    private var imageBuffer = ByteBuffer.allocateDirect(0).order(ByteOrder.nativeOrder())

    private val verticesData by lazy {
        ByteBuffer
            .allocateDirect(quadVertices.size * Float.SIZE_BYTES)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .also { it.put(quadVertices) }
    }

    private fun createAndVerifyShader(shaderCode: String, shaderType: Int): Int {
        val shaderId = glCreateShader(shaderType)
        glShaderSource(shaderId, shaderCode)
        glCompileShader(shaderId)
        val compileStatusArr = IntArray(1)
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, compileStatusArr, 0)
        return if(compileStatusArr[0] == 0) 0 else shaderId
    }

    private fun setupProgram() {
        programId?.let(::glDeleteProgram)
        val programId = glCreateProgram()
        this.programId = programId
        if(programId == 0) return
        val fragShader = createAndVerifyShader(fragmentShader, GL_FRAGMENT_SHADER)
        val vertShader = createAndVerifyShader(vertexShader, GL_VERTEX_SHADER)
        glAttachShader(programId, vertShader)
        glAttachShader(programId, fragShader)
        glLinkProgram(programId)
        val linkStatusArr = IntArray(1)
        glGetProgramiv(programId, GL_LINK_STATUS, linkStatusArr, 0)
        if(linkStatusArr[0] == 0) {
            glDeleteProgram(programId)
            return
        }
        glValidateProgram(programId)
        val validationStatusArr = IntArray(1)
        glGetProgramiv(programId, GL_VALIDATE_STATUS, validationStatusArr, 0)
        if(validationStatusArr[0] == 0) return
        positionAttributeLocation = glGetAttribLocation(programId, "a_position")
        resolutionUniformLocation = glGetUniformLocation(programId, "u_resolution")
        timeUniformLocation = glGetUniformLocation(programId, "u_time")
        verticesData.position(0)
        positionAttributeLocation?.let {
            glVertexAttribPointer(it, 2, GL_FLOAT, false, 0, verticesData)
        }
        glDetachShader(programId, vertShader)
        glDetachShader(programId, fragShader)
        glDeleteShader(vertShader)
        glDeleteShader(fragShader)
    }

    private fun generateSampleShaderBitmap(): Bitmap {
        val thirdWidth = surfaceWidth / 3
        val thirdHeight = surfaceHeight / 3
        glReadPixels(
            thirdWidth,
            thirdHeight,
            thirdWidth * 2,
            thirdHeight * 2,
            GL_RGBA,
            GL_UNSIGNED_BYTE,
            imageBuffer
        )
        val bitmap = Bitmap.createBitmap(24, 24, Bitmap.Config.ARGB_8888)
        bitmap.copyPixelsFromBuffer(imageBuffer)
        return bitmap
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0f, 0f, 0f, 1f)
        glDisable(GL_DITHER)
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_FASTEST)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
        surfaceWidth = width
        surfaceHeight = height
        frameCount = 0f
        imageBuffer = ByteBuffer.allocateDirect(width * height * Float.SIZE_BYTES)
            .order(ByteOrder.nativeOrder())
    }

    override fun onDrawFrame(p0: GL10?) {
        if(!shouldPlay.get()) return
        glClear(GL_COLOR_BUFFER_BIT)
        if(isProgramChanged.getAndSet(false)) setupProgram()
        else programId?.let(::glUseProgram) ?: return
        positionAttributeLocation?.let(::glEnableVertexAttribArray) ?: return
        resolutionUniformLocation?.let { glUniform2f(it, surfaceWidth.toFloat(), surfaceHeight.toFloat()) }
        timeUniformLocation?.let { glUniform1f(it, frameCount) }
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4)
        positionAttributeLocation?.let(::glDisableVertexAttribArray) ?: return
        frameCount = frameCount % 30 + 0.01f
        sampleShaderBitmapCallback?.let {
            if(surfaceWidth == 0 || surfaceHeight == 0) return@let
            val bitmap = generateSampleShaderBitmap()
            it(bitmap)
            sampleShaderBitmapCallback = null
            bitmap.recycle()
        }
    }

    fun onResume() { shouldPlay.compareAndSet(false, ::fragmentShader.isInitialized) }

    fun onPause() { shouldPlay.compareAndSet(true, false) }

    fun setSampleShaderImageCallback(callback: ((Bitmap) -> Unit)?) {
        sampleShaderBitmapCallback = callback
    }

    fun setShaders(fragmentShader: String, vertexShader: String) {
        this.fragmentShader = fragmentShader
        this.vertexShader = vertexShader
        shouldPlay.compareAndSet(false, true)
        isProgramChanged.compareAndSet(false, true)
    }

}
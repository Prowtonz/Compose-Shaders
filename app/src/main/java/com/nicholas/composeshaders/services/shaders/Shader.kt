package com.nicholas.composeshaders.services.shaders

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shader(
    val id: Int,
    val title: String,
    val description: String,
    val fragmentShader: String,
    val vertexShader: String
) : Parcelable

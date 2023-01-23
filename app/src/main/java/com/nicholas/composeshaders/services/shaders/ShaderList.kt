package com.nicholas.composeshaders.services.shaders

import android.content.Context
import androidx.annotation.RawRes
import com.nicholas.composeshaders.R
import java.io.BufferedReader
import java.io.InputStreamReader

fun getShaderList(context: Context): List<Shader> {
    val description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
            " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when " +
            "an unknown printer took a galley of type and scrambled it to make a type specimen book."
    val vertexShader = context.readTextFromFile(R.raw.simple_vertex_shader)
    return listOf(
        Shader(
            0,
            "Inferno",
            description,
            context.readTextFromFile(R.raw.inferno),
            vertexShader
        ),
        Shader(
            1,
            "Rhythm of the Heart",
            description,
            context.readTextFromFile(R.raw.rhythm_of_heart),
            vertexShader
        ),
        Shader(
            2,
            "Sine Flower",
            description,
            context.readTextFromFile(R.raw.sine_flower),
            vertexShader
        ),
        Shader(
            3,
            "Strings Attached",
            description,
            context.readTextFromFile(R.raw.strings_attached),
            vertexShader
        ),
        Shader(
            4,
            "Jelly Springs",
            description,
            context.readTextFromFile(R.raw.jelly_springs),
            vertexShader
        ),
        Shader(
            5,
            "Starry Shimmer",
            description,
            context.readTextFromFile(R.raw.starry_shimmer),
            vertexShader
        ),
        Shader(
            6,
            "Polka Shade",
            description,
            context.readTextFromFile(R.raw.polka_shade),
            vertexShader
        ),
        Shader(
            7,
            "Vortex",
            description,
            context.readTextFromFile(R.raw.vortex),
            vertexShader
        ),
        Shader(
            8,
            "Digital DNA",
            description,
            context.readTextFromFile(R.raw.dna),
            vertexShader
        ),
        Shader(
            9,
            "Hypnotising Spiral",
            description,
            context.readTextFromFile(R.raw.hypnotising_spiral),
            vertexShader
        ),
        Shader(
            10,
            "Monochrome Iris",
            description,
            context.readTextFromFile(R.raw.monochrome_iris),
            vertexShader
        ),
        Shader(
            11,
            "Raining Shurikens",
            description,
            context.readTextFromFile(R.raw.shuriken_rain),
            vertexShader
        ),
        Shader(
            12,
            "Warped Memories",
            description,
            context.readTextFromFile(R.raw.warped_memories),
            vertexShader
        ),
        Shader(
            13,
            "Monochrome Ratio",
            description,
            context.readTextFromFile(R.raw.monochrome_ratio),
            vertexShader
        )
    )
}

private fun Context.readTextFromFile(@RawRes resourceId: Int): String {
    val retVal = StringBuilder()
    val inputStream = resources.openRawResource(resourceId)
    val reader = BufferedReader(InputStreamReader(inputStream))
    var line = reader.readLine()
    while(line != null) {
        retVal.append(line + '\n')
        line = reader.readLine()
    }
    return retVal.toString()
}
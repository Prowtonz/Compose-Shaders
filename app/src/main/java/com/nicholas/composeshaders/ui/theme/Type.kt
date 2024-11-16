package com.nicholas.composeshaders.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nicholas.composeshaders.R

private val BlackMango = FontFamily(
    listOf(
        Font(R.font.black_mango_regular, FontWeight.Normal),
        Font(R.font.black_mango_medium, FontWeight.Medium),
        Font(R.font.black_mango_bold, FontWeight.Bold),
        Font(R.font.black_mango_black, FontWeight.Black),
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = BlackMango,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = BlackMango,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = 2.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
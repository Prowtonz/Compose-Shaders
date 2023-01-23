package com.nicholas.composeshaders.ui.screens.shaderdemo.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ButtonState(
    val showLoader: Boolean = false,
    val showSuccessMessage: Boolean = false
) : Parcelable

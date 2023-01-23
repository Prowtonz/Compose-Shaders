package com.nicholas.composeshaders.ui.screens.shaderdemo.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ButtonColorHolder(var backgroundColor: Int, var textColor: Int) : Parcelable

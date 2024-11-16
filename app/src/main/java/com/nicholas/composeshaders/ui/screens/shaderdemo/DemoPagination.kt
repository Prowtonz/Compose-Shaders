package com.nicholas.composeshaders.ui.screens.shaderdemo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nicholas.composeshaders.R
import com.nicholas.composeshaders.ui.theme.PrimaryTextColor

@Composable
fun DemoPagination(
    modifier: Modifier = Modifier,
    shaderIndex: Int,
    count: Int,
    onNavigate: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(Modifier.size(36.dp)) {
            if(shaderIndex > 0) Icon(
                painter = painterResource(R.drawable.ic_left_arrow),
                contentDescription = null,
                tint = PrimaryTextColor,
                modifier = Modifier
                    .fillMaxSize()
                    .border(BorderStroke(1.dp, PrimaryTextColor.copy(0.5f)), CircleShape)
                    .clip(CircleShape)
                    .clickable { onNavigate(shaderIndex - 1) }
            )
        }
        Box(Modifier.size(36.dp)) {
            if(shaderIndex < count - 1) Icon(
                painter = painterResource(R.drawable.ic_left_arrow),
                contentDescription = null,
                tint = PrimaryTextColor,
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(180f)
                    .border(BorderStroke(1.dp, PrimaryTextColor.copy(0.5f)), CircleShape)
                    .clip(CircleShape)
                    .clickable { onNavigate(shaderIndex + 1) }
            )
        }
    }
}
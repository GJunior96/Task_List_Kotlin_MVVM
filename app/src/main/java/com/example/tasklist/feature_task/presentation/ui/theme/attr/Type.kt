package com.example.tasklist.feature_task.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.sp
import com.example.tasklist.R

val pleaseWriteMeASong = FontFamily(
    Font(R.font.please_write_me_a_song)
)

val poppins = FontFamily(
    Font(R.font.poppins_regular, weight = FontWeight.Normal),
    Font(R.font.poppins_bold, weight = FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = pleaseWriteMeASong,
        fontSize = 35.sp
    ),

    h1 = TextStyle(
        fontFamily = pleaseWriteMeASong,
        fontSize = 43.sp
    ),

    h2 = TextStyle(
        fontFamily = poppins,
        fontSize = 22.sp
    ),

    h3 = TextStyle(
        fontFamily = pleaseWriteMeASong,
        fontSize = 24.sp
    ),

    h4 = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
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
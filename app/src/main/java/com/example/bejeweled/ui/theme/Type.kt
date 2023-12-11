package com.example.bejeweled.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.bejeweled.R


val londrinaSketchFamily = FontFamily(
    Font(R.font.londrinasketch_regular, FontWeight.Bold),
)
val londrinaSolidFamily = FontFamily(
    Font(R.font.londrinasolid_regular, FontWeight.Bold),
    Font(R.font.londrinasolid_light, FontWeight.Light),
    Font(R.font.londrinasolid_thin, FontWeight.Thin),
    Font(R.font.londrinasolid_black, FontWeight.Black),
)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
     displayLarge = TextStyle(
        fontFamily = londrinaSolidFamily,
        fontWeight = FontWeight.Black,
        fontSize = 70.sp
    ),
    titleLarge = TextStyle(
        fontFamily = londrinaSolidFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp
    ),
    titleMedium = TextStyle(
        fontFamily = londrinaSolidFamily,
        fontWeight = FontWeight.Light,
        fontSize = 18.sp
    ),


)
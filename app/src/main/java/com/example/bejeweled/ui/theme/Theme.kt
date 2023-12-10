package com.example.bejeweled.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.Color
import com.example.bejeweled.ui.theme.ThemeOption.*

@Composable
fun BejeweledTheme(
    selectedTheme: ThemeOption = ThemeOption.LIGHT,
    content: @Composable (brush: Brush) -> Unit
) {
    val colorScheme = when (selectedTheme) {
        ThemeOption.DARK -> darkColorScheme(
            primary = DarkRed,
            onPrimary = DarkGray,
            surface = Black
        )
        else -> lightColorScheme(
            primary = Purple40,
            onPrimary = PurpleGrey40,
            surface = White
        )
    }
    val themeGradient = when(selectedTheme) {
        LIGHT -> gradientLight
        DARK -> gradientDark
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                selectedTheme == ThemeOption.LIGHT
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes
    ) {
        content(themeGradient)
    }
}

enum class ThemeOption {
    LIGHT,
    DARK
}
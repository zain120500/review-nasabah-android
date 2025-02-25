package com.example.myapplication.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val customLightColorScheme = lightColorScheme(
    primary = Color(0xFF1E73BE),
    onPrimary = Color.White,
    secondary = Color(0xFF6200EE),
    onSecondary = Color.White,
    background = Color(0xFFF0F0F0),
    surface = Color.White,
    onSurface = Color.Black,
)

val customDarkColorScheme = darkColorScheme(
    primary = Color(0xFF1E73BE),
    onPrimary = Color.Black,
    secondary = Color(0xFF6200EE),
    onSecondary = Color.Black,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
)

data class FixedAccentColors(
    val primaryFixed: Color,
    val onPrimaryFixed: Color,
    val secondaryFixed: Color,
    val onSecondaryFixed: Color,
    val tertiaryFixed: Color,
    val onTertiaryFixed: Color,
    val primaryFixedDim: Color,
    val secondaryFixedDim: Color,
    val tertiaryFixedDim: Color,
)

fun getFixedAccentColors() =
    FixedAccentColors(
        primaryFixed = customLightColorScheme.primary,
        onPrimaryFixed = customLightColorScheme.onPrimary,
        secondaryFixed = customLightColorScheme.secondary,
        onSecondaryFixed = customLightColorScheme.onSecondary,
        tertiaryFixed = customLightColorScheme.surface,
        onTertiaryFixed = customLightColorScheme.onSurface,
        primaryFixedDim = customDarkColorScheme.primary,
        secondaryFixedDim = customDarkColorScheme.secondary,
        tertiaryFixedDim = customDarkColorScheme.surface
    )

val LocalFixedAccentColors = compositionLocalOf { getFixedAccentColors() }


@Composable
fun MyTheme(
    fixedAccentColors: FixedAccentColors = LocalFixedAccentColors.current,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) customDarkColorScheme else customLightColorScheme
    ) {
        CompositionLocalProvider(LocalFixedAccentColors provides fixedAccentColors) {
            content()
        }
    }
}

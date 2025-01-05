package cmc.goalmate.ui.theme.color

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

data class GoalMateColors(
    val material: ColorScheme,
    val onPrimaryAlt: Color = material.onPrimary,
    val secondary01: Color = material.secondary,
    val secondary02: Color = material.secondary,
    val secondary03: Color = material.secondary,
    val success: Color = Color.Green,
) {
    val primary: Color get() = material.primary
    val primaryVariant: Color get() = material.primaryContainer
    val secondary: Color get() = material.secondary
    val secondaryVariant: Color get() = material.secondaryContainer
    val background: Color get() = material.background
    val surface: Color get() = material.surface
    val error: Color get() = material.error
    val onPrimary: Color get() = material.onPrimary
    val onSecondary: Color get() = material.onSecondary
    val onBackground: Color get() = material.onBackground
    val onSurface: Color get() = material.onSurface
    val onError: Color get() = material.onError
}

package cmc.goalmate.presentation.theme.color

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

data class GoalMateColors(
    val material: ColorScheme,
    val secondary01: Color,
    val secondary02: Color,
    val onSecondary02: Color,
    val secondary03: Color,
    val success: Color,
    val selected: Color,
    val unSelected: Color,
    val pending: Color,
    val completed: Color,
    val disabled: Color,
    val onDisabled: Color,
    val outline: Color,
    val checkboxBackground: Color,
    val labelTitle: Color,
    val primaryVariant: Color,
    val onPrimaryVariant: Color,
    val secondaryVariant: Color,
    val onSecondaryVariant: Color,
    val thinDivider: Color,
    val thickDivider: Color,
    val finished: Color,
    val activeProgressBar: Color,
    val activeProgressBackground: Color,
    val completedProgressBar: Color,
    val completedProgressBackground: Color,
    val textButton: Color,
) {
    val primary: Color get() = material.primary
    val secondary: Color get() = material.secondary
    val background: Color get() = material.background
    val surface: Color get() = material.surface
    val error: Color get() = material.error
    val onPrimary: Color get() = material.onPrimary
    val onSecondary: Color get() = material.onSecondary
    val onBackground: Color get() = material.onBackground
    val onSurface: Color get() = material.onSurface
    val onError: Color get() = material.onError
    val surfaceVariant: Color get() = material.surfaceVariant
    val onSurfaceVariant: Color get() = material.onSurfaceVariant
}

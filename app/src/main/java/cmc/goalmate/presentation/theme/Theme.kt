package cmc.goalmate.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import cmc.goalmate.presentation.theme.color.ColorSet
import cmc.goalmate.presentation.theme.color.GoalMateColors

private val LocalTypography = staticCompositionLocalOf { goalMateTypography }
private val LocalColors = staticCompositionLocalOf<GoalMateColors> { error("No GoalMateColors provided") }

@Composable
fun GoalMateTheme(
    colorSet: ColorSet = ColorSet.GoalMateColor,
    typography: GoalMateTypography = goalMateTypography,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = remember(darkTheme) {
        if (darkTheme) colorSet.darkColors else colorSet.lightColors
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography,
    ) {
        MaterialTheme(
            colorScheme = colors.material,
            content = content,
        )
    }
}

val MaterialTheme.goalMateTypography: GoalMateTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalTypography.current

val MaterialTheme.goalMateColors: GoalMateColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current

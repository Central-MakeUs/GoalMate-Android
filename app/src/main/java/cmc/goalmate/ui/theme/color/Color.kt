package cmc.goalmate.ui.theme.color

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Red = Color(0xFFFF3B30)
val Green = Color(0xFF34C759)
val Yellow = Color(0xFFFFCC00)
val Blue = Color(0xFF007AFF)

val LightGreen400 = Color(0xFFAEFF23)
val Purple500 = Color(0xFF7423FF)
val YellowBright500 = Color(0xFFFFE223)
val GreenBright400 = Color(0xFF40FF23)

val Grey50 = Color(0xFFFAFAFA)
val Grey100 = Color(0xFFF5F5F5)
val Grey200 = Color(0xFFEEEEEE)
val Grey300 = Color(0xFFE0E0E0)
val Grey400 = Color(0xFFBDBDBD)
val Grey500 = Color(0xFF9E9E9E)
val Grey600 = Color(0xFF757575)
val Grey700 = Color(0xFF616161)
val Grey800 = Color(0xFF424242)
val Grey900 = Color(0xFF212121)

sealed class ColorSet {
    abstract val lightColors: GoalMateColors
    abstract val darkColors: GoalMateColors

    data object GoalMateColor : ColorSet() {
        override var lightColors = GoalMateColors(
            material = lightColorScheme(
                primary = LightGreen400,
                onPrimary = Black,
                secondary = Purple500,
                surface = White,
                onSurface = Grey800,
                background = White,
                onBackground = Grey900,
                error = Red,
            ),
            secondary01 = Purple500,
            secondary02 = YellowBright500,
            secondary03 = GreenBright400,
            success = Blue,
        )

        override var darkColors = GoalMateColors(material = darkColorScheme())
    }
}

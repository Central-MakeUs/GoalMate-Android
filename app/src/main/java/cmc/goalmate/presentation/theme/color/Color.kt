package cmc.goalmate.presentation.theme.color

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Red = Color(0xFFFF3B30)
val ErrorBackground = Color(0xFFFFEAE9)
val Green = Color(0xFF34C759)
val Yellow = Color(0xFFFFCC00)
val Blue = Color(0xFF007AFF)

val Primary10 = Color(0xFFFBFFF5)
val Primary400 = Color(0xFFAEFF23)
val Primary700 = Color(0xFF8BD300)
val Primary900 = Color(0xFF709100)
val Primary50 = Color(0xFFF4FFE5)
val Primary100 = Color(0xFFE5FFBE)

val Secondary03 = Color(0xFFF7F2FF)

val Secondary01_400 = Color(0xFF8D4EFF)
val Secondary01_500 = Color(0xFF7423FF)
val Secondary01_50 = Color(0xFFF1E7FF)
val YellowBright500 = Color(0xFFFFE223)
val GreenBright400 = Color(0xFF40FF23)
val Secondary02_50 = Color(0xFFFFFCE5)
val Secondary02_800 = Color(0xFFF99E13)
val Secondary02_700 = Color(0xFFFBB719)

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
                primary = Primary400,
                onPrimary = Black,
                secondary = Secondary01_400,
                onSecondary = White,
                surface = Grey200,
                onSurface = Grey800,
                background = White,
                onBackground = Grey900,
                error = ErrorBackground,
                onError = Red,
                surfaceVariant = Grey50,
                onSurfaceVariant = Grey600,
            ),
            secondary01 = Secondary01_500,
            secondary02 = YellowBright500,
            onSecondary02 = Grey800,
            secondary03 = GreenBright400,
            success = Blue,
            selected = Grey800,
            unSelected = Grey400,
            pending = Grey200,
            completed = Primary100,
            disabled = Grey300,
            onDisabled = White,
            outline = Grey300,
            checkboxBackground = Grey200,
            labelTitle = Grey500,
            primaryVariant = Primary50,
            onPrimaryVariant = Primary900,
            secondary02Variant = Secondary02_50,
            onSecondary02Variant = Secondary02_800,
            secondary01Variant = Secondary01_50,
            thinDivider = Grey100,
            thickDivider = Grey50,
            finished = Grey500,
            activeProgressBar = Primary400,
            activeProgressBackground = Primary50,
            completedProgressBar = Grey400,
            completedProgressBackground = Grey100,
            textButton = Grey700,
        )

        override var darkColors = GoalMateColors(
            material = darkColorScheme(),
            secondary01 = Secondary01_500,
            secondary02 = YellowBright500,
            onSecondary02 = Grey800,
            secondary03 = GreenBright400,
            success = Blue,
            selected = Grey800,
            unSelected = Grey400,
            pending = Grey200,
            completed = Primary100,
            disabled = Grey300,
            onDisabled = White,
            outline = Grey300,
            checkboxBackground = Grey200,
            labelTitle = Grey500,
            primaryVariant = Primary50,
            onPrimaryVariant = Primary900,
            secondary02Variant = Secondary02_50,
            onSecondary02Variant = Secondary02_800,
            secondary01Variant = Secondary01_50,
            thinDivider = Grey100,
            thickDivider = Grey50,
            finished = Grey500,
            activeProgressBar = Primary400,
            activeProgressBackground = Primary50,
            completedProgressBar = Grey400,
            completedProgressBackground = Grey100,
            textButton = Grey700,
        )
    }
}

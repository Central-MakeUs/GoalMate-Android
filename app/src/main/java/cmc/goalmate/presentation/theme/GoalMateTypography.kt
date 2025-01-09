package cmc.goalmate.presentation.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cmc.goalmate.R

private val pretendardBold =
    FontFamily(
        Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal),
    )

private val pretendardRegular =
    FontFamily(
        Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal),
    )

private val pretendardSemiBold =
    FontFamily(
        Font(R.font.pretendard_semibold, FontWeight.Normal, FontStyle.Normal),
    )

data class GoalMateTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val subtitle: TextStyle,
    val subtitleSmall: TextStyle,
    val body: TextStyle,
    val bodySmall: TextStyle,
    val buttonLabelLarge: TextStyle,
    val buttonLabelSmall: TextStyle,
    val caption: TextStyle,
)

private val H1 =
    TextStyle(
        fontFamily = pretendardBold,
        fontSize = 36.sp,
        lineHeight = 46.8.sp,
    )

private val H2 =
    TextStyle(
        fontFamily = pretendardBold,
        fontSize = 32.sp,
        lineHeight = 41.6.sp,
    )

private val H3 =
    TextStyle(
        fontFamily = pretendardBold,
        fontSize = 28.sp,
        lineHeight = 36.4.sp,
    )

private val H4 =
    TextStyle(
        fontFamily = pretendardBold,
        fontSize = 24.sp,
        lineHeight = 31.2.sp,
    )

private val Subtitle =
    TextStyle(
        fontFamily = pretendardRegular,
        fontSize = 20.sp,
        lineHeight = 26.sp,
    )

private val SubtitleSmall =
    TextStyle(
        fontFamily = pretendardRegular,
        fontSize = 18.sp,
        lineHeight = 23.4.sp,
    )

private val Body =
    TextStyle(
        fontFamily = pretendardRegular,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    )

private val BodySmall =
    TextStyle(
        fontFamily = pretendardRegular,
        fontSize = 14.sp,
        lineHeight = 21.sp,
    )

private val ButtonLabelLarge =
    TextStyle(
        fontFamily = pretendardSemiBold,
        fontSize = 16.sp,
        lineHeight = 25.6.sp,
    )

private val ButtonLabelSmall =
    TextStyle(
        fontFamily = pretendardSemiBold,
        fontSize = 14.sp,
        lineHeight = 14.sp,
    )

private val Caption =
    TextStyle(
        fontFamily = pretendardRegular,
        fontSize = 12.sp,
        lineHeight = 12.sp,
    )

val goalMateTypography =
    GoalMateTypography(
        h1 = H1,
        h2 = H2,
        h3 = H3,
        h4 = H4,
        subtitle = Subtitle,
        subtitleSmall = SubtitleSmall,
        body = Body,
        bodySmall = BodySmall,
        buttonLabelLarge = ButtonLabelLarge,
        buttonLabelSmall = ButtonLabelSmall,
        caption = Caption,
    )

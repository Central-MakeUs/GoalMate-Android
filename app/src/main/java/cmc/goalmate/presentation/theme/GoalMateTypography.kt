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
        Font(R.font.pretendard_semibold, FontWeight.SemiBold, FontStyle.Normal),
    )

private val pretendardMedium =
    FontFamily(
        Font(R.font.pretendard_medium, FontWeight.Medium, FontStyle.Normal),
    )

data class GoalMateTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val h5: TextStyle,
    val subtitle: TextStyle,
    val subtitleMedium: TextStyle,
    val subtitleSmall: TextStyle,
    val body: TextStyle,
    val bodySmall: TextStyle,
    val bodySmallMedium: TextStyle,
    val buttonLabelLarge: TextStyle,
    val buttonLabelMedium: TextStyle,
    val buttonLabelSmall: TextStyle,
    val caption: TextStyle,
    val captionMedium: TextStyle,
    val captionSemiBold: TextStyle,
    val captionRegular: TextStyle,
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

private val H5 =
    TextStyle(
        fontFamily = pretendardSemiBold,
        fontSize = 22.sp,
        lineHeight = 28.6.sp,
    )

private val Subtitle =
    TextStyle(
        fontFamily = pretendardSemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
    )

private val SubtitleMedium =
    TextStyle(
        fontFamily = pretendardSemiBold,
        fontSize = 18.sp,
        lineHeight = 23.4.sp,
    )

private val SubtitleSmall =
    TextStyle(
        fontFamily = pretendardSemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
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

private val BodySmallMedium =
    TextStyle(
        fontFamily = pretendardMedium,
        fontSize = 14.sp,
        lineHeight = 21.sp,
    )

private val ButtonLabelLarge =
    TextStyle(
        fontFamily = pretendardMedium,
        fontSize = 16.sp,
        lineHeight = 19.09.sp,
    )

private val ButtonLabelMedium =
    TextStyle(
        fontFamily = pretendardMedium,
        fontSize = 12.sp,
        lineHeight = 12.sp,
    )

private val ButtonLabelSmall =
    TextStyle(
        fontFamily = pretendardSemiBold,
        fontSize = 14.sp,
        lineHeight = 14.sp,
    )

private val CaptionMedium =
    TextStyle(
        fontFamily = pretendardMedium,
        fontSize = 13.sp,
        lineHeight = 19.5.sp,
    )

private val CaptionSemibold =
    TextStyle(
        fontFamily = pretendardSemiBold,
        fontSize = 13.sp,
        lineHeight = 19.5.sp,
    )

private val CaptionRegular =
    TextStyle(
        fontFamily = pretendardRegular,
        fontSize = 13.sp,
        lineHeight = 19.5.sp,
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
        h5 = H5,
        subtitle = Subtitle,
        subtitleMedium = SubtitleMedium,
        subtitleSmall = SubtitleSmall,
        body = Body,
        bodySmall = BodySmall,
        bodySmallMedium = BodySmallMedium,
        buttonLabelLarge = ButtonLabelLarge,
        buttonLabelMedium = ButtonLabelMedium,
        buttonLabelSmall = ButtonLabelSmall,
        caption = Caption,
        captionMedium = CaptionMedium,
        captionSemiBold = CaptionSemibold,
        captionRegular = CaptionRegular,
    )

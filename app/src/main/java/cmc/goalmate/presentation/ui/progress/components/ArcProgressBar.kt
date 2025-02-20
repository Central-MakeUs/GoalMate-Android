package cmc.goalmate.presentation.ui.progress.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.Grey700
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.progress.components.ProgressMessage.Companion.convertDisplayPercent

private const val MIN_SWIPE_ANGLE = 1f
private const val MAX_SWIPE_ANGLE = 180f
private const val START_ANGLE = 180f

@Composable
fun ArcProgressBar(
    percent: Float,
    modifier: Modifier = Modifier,
) {
    val barColor = MaterialTheme.goalMateColors.secondary02
    val backgroundColor = MaterialTheme.goalMateColors.secondary02Variant

    val adjustedPercent = MIN_SWIPE_ANGLE + (percent / 100f) * (MAX_SWIPE_ANGLE - MIN_SWIPE_ANGLE)

    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier.size(240.dp, 140.dp).padding(20.dp),
        ) {
            drawArc(
                color = backgroundColor,
                startAngle = START_ANGLE,
                sweepAngle = MAX_SWIPE_ANGLE,
                useCenter = false,
                size = Size(200.dp.toPx(), 200.dp.toPx()),
                style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round),
            )
            drawArc(
                color = barColor,
                startAngle = START_ANGLE,
                sweepAngle = adjustedPercent,
                useCenter = false,
                size = Size(200.dp.toPx(), 200.dp.toPx()),
                style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round),
            )
        }
    }
}

@Composable
fun TodayProgress(
    actualPercent: Float,
    modifier: Modifier = Modifier,
) {
    val displayPercent = convertDisplayPercent(actualPercent)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box {
            ArcProgressBar(percent = displayPercent.toFloat())
            Text(
                text = "$displayPercent%",
                style = MaterialTheme.goalMateTypography.subtitle,
                color = Grey700,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 25.dp),
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.goalMateColors.thinDivider,
                    shape = RoundedCornerShape(14.dp),
                )
                .padding(vertical = 12.dp, horizontal = 10.dp),
        ) {
            Text(
                text = ProgressMessage.getMessageForProgress(displayPercent),
                style = MaterialTheme.goalMateTypography.bodySmallMedium,
                color = Grey700,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

enum class ProgressMessage(val range: IntRange, val message: String) {
    NOT_STARTED(0..0, "미션을 시작해주세요"),
    EARLY_PROGRESS(1..9, "시작이 반이에요!"),
    PROGRESS_10(10..19, "시작이 반이에요!"),
    PROGRESS_20(20..29, "시작이 반이에요!"),
    PROGRESS_30(30..39, "좋아요. 잘 하고 있어요!"),
    PROGRESS_40(40..49, "좋아요. 잘 하고 있어요!"),
    PROGRESS_50(50..59, "벌써 절반이에요!"),
    PROGRESS_60(60..69, "벌써 절반이에요!"),
    PROGRESS_70(70..79, "조금만 더 힘내요, 얼마 안 남았어요!"),
    PROGRESS_80(80..89, "조금만 더 힘내요, 얼마 안 남았어요!"),
    PROGRESS_90(90..99, "마무리까지 힘내요!"),
    COMPLETE(100..100, "수고했어요! 오늘의 목표를 완료했어요."),
    ;

    companion object {
        fun getMessageForProgress(progress: Int): String =
            requireNotNull(entries.find { progress in it.range }?.message) { "유효하지 않은 progress : $progress" }

        fun convertDisplayPercent(actual: Float): Int = (actual * 100).toInt()
    }
}

@Composable
@Preview(showBackground = true)
private fun ArcProgressBarPreview() {
    GoalMateTheme {
        TodayProgress(
            actualPercent = 0.5f,
        )
    }
}

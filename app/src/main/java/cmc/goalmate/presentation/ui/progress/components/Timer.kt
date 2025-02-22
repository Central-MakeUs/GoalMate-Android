package cmc.goalmate.presentation.ui.progress.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.progress.components.TimerStatus.EXPIRED
import cmc.goalmate.presentation.ui.progress.components.TimerStatus.RUNNING
import cmc.goalmate.presentation.ui.progress.components.TimerStatus.URGENT
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.Locale

enum class TimerStatus {
    RUNNING,
    URGENT,
    EXPIRED,
}

@Composable
private fun TimerStatus.textColor(): Color =
    when (this) {
        URGENT -> MaterialTheme.goalMateColors.onError
        else -> MaterialTheme.goalMateColors.textButton
    }

@Composable
private fun TimerStatus.backgroundColor(): Color =
    when (this) {
        URGENT -> MaterialTheme.goalMateColors.error
        else -> MaterialTheme.goalMateColors.background
    }

@Composable
private fun TimerStatus.borderColor(): Color =
    when (this) {
        URGENT -> MaterialTheme.goalMateColors.onError
        else -> MaterialTheme.goalMateColors.outline
    }

@Composable
fun GoalMateTimer(
    timerStatus: TimerStatus,
    modifier: Modifier = Modifier,
) {
    var remainingTime by remember { mutableLongStateOf(getTimeUntilMidnight()) }

    LaunchedEffect(timerStatus) {
        if (timerStatus == RUNNING) {
            while (remainingTime > 0) {
                delay(1000L)
                remainingTime = getTimeUntilMidnight()
            }
        }
    }

    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = timerStatus.borderColor(),
                shape = RoundedCornerShape(12.dp),
            )
            .background(
                color = timerStatus.backgroundColor(),
                shape = RoundedCornerShape(12.dp),
            )
            .padding(vertical = 6.dp, horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        if (timerStatus == EXPIRED) {
            Text(
                text = "time over",
                style = MaterialTheme.goalMateTypography.bodySmallMedium,
                color = timerStatus.textColor(),
            )
            return
        }
        Text(
            text = formatDuration(remainingTime),
            style = MaterialTheme.goalMateTypography.bodySmallMedium,
            color = timerStatus.textColor(),
        )
        Spacer(Modifier.size(4.dp))
        Text(
            text = "남았어요",
            style = MaterialTheme.goalMateTypography.caption,
            color = timerStatus.textColor(),
        )
    }
}

fun getTimeUntilMidnight(): Long {
    val now = LocalDateTime.now()
    val midnight = LocalDate.now().atStartOfDay().plusDays(1)
    return ChronoUnit.SECONDS.between(now, midnight).coerceAtLeast(0)
}

fun formatDuration(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, secs)
}

@Composable
@Preview
private fun GoalMateTimerPreview() {
    GoalMateTheme {
        GoalMateTimer(
            timerStatus = RUNNING,
        )
    }
}

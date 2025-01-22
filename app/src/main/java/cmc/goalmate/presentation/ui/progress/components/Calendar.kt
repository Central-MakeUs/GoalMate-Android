package cmc.goalmate.presentation.ui.progress.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.Grey200
import cmc.goalmate.presentation.theme.color.Grey400
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.progress.inprogress.model.CalendarUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressUiModel
import java.time.format.DateTimeFormatter

enum class DayOfWeek(val label: String) {
    SUNDAY("일"),
    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토"),
}

@Composable
fun GoalMateCalendar(
    calendarUiModel: CalendarUiModel,
    selectedDate: Int,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = { 1 })

    Column(
        modifier = modifier,
    ) {
        YearMonthHeader(
            label = (calendarUiModel.yearMonth).format(DateTimeFormatter.ofPattern("yy년 M월")),
            hasNext = calendarUiModel.hasNext,
            hasPrevious = calendarUiModel.hasPrevious,
            onNextClicked = {},
            onPreviousClicked = {},
            modifier = Modifier.align(Alignment.Start),
        )

        Spacer(Modifier.size(20.dp))

        WeekRow(modifier = Modifier.fillMaxWidth()) {
            DayOfWeek.entries.forEach { day ->
                Text(
                    text = day.label,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.goalMateTypography.captionMedium,
                    color = MaterialTheme.goalMateColors.labelTitle,
                    modifier = Modifier.size(CIRCLE_SIZE.dp),
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            WeeklyProgressItem(
                progressByDate = calendarUiModel.progressByDate,
                selectedDate = selectedDate,
                onDateClicked = {
                    // TODO: 날짜 클릭시
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(Modifier.size(16.dp))
    }
}

@Composable
private fun YearMonthHeader(
    label: String,
    hasNext: Boolean,
    hasPrevious: Boolean,
    onNextClicked: () -> Unit,
    onPreviousClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onPreviousClicked,
            enabled = hasPrevious,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_before),
                contentDescription = null,
                tint = getButtonTint(hasPrevious),
            )
        }

        Text(
            text = label,
            style = MaterialTheme.goalMateTypography.captionSemiBold,
            color = MaterialTheme.goalMateColors.textButton,
            modifier = Modifier
                .background(
                    color = MaterialTheme.goalMateColors.outline,
                    shape = RoundedCornerShape(6.dp),
                )
                .padding(vertical = 6.dp, horizontal = 8.dp),
        )

        IconButton(
            onClick = onNextClicked,
            enabled = hasNext,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_next),
                contentDescription = null,
                tint = getButtonTint(hasNext),
            )
        }
    }
}

@Composable
private fun getButtonTint(isEnabled: Boolean): Color = if (isEnabled) Grey400 else Grey200

@Composable
fun WeekRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}

@Composable
private fun WeeklyProgressItem(
    progressByDate: List<DailyProgressUiModel>,
    selectedDate: Int,
    onDateClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    WeekRow(modifier = modifier) {
        progressByDate.forEach {
            CircleProgressBar(
                date = it.date,
                status = it.status,
                onClick = onDateClicked,
                isSelected = it.date == selectedDate,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun GoalStartScreenPreview() {
    GoalMateTheme {
        GoalMateCalendar(
            calendarUiModel = CalendarUiModel.DUMMY,
            selectedDate = 25,
        )
    }
}

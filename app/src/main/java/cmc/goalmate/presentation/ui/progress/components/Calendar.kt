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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.progress.inprogress.InProgressAction
import cmc.goalmate.presentation.ui.progress.inprogress.model.CalendarUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.ProgressUiState
import java.time.LocalDate
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

private val goalMateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")

@Composable
fun GoalMateCalendar(
    calendarData: CalendarUiModel,
    selectedDate: LocalDate,
    onAction: (InProgressAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = selectedDate.format(goalMateFormatter),
            style = MaterialTheme.goalMateTypography.bodySmall,
            color = MaterialTheme.goalMateColors.textButton,
            modifier = Modifier
                .background(
                    color = MaterialTheme.goalMateColors.surface,
                    shape = RoundedCornerShape(6.dp),
                )
                .padding(vertical = 6.dp, horizontal = 8.dp),
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

        WeeklyCalendar(
            initialWeekNumber = calendarData.todayWeekNumber - 1,
            weeklyData = calendarData.weeklyData,
            selectedDate = selectedDate,
            onAction = onAction,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.size(16.dp))
    }
}

@Composable
fun WeekRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}

@Composable
private fun WeeklyCalendar(
    initialWeekNumber: Int,
    weeklyData: List<List<DailyProgressUiModel>>,
    selectedDate: LocalDate,
    onAction: (InProgressAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = { weeklyData.size }, initialPage = initialWeekNumber)
    var previousPage by remember { mutableStateOf(pagerState.currentPage) }

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage < previousPage) {
            onAction(InProgressAction.SwipeLeft(pagerState.currentPage))
        }
        previousPage = pagerState.currentPage
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
    ) {
        WeeklyProgressItem(
            progressByDate = weeklyData[pagerState.currentPage],
            selectedDate = selectedDate,
            onDateClicked = { onAction(InProgressAction.SelectDate(it)) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun WeeklyProgressItem(
    progressByDate: List<DailyProgressUiModel>,
    selectedDate: LocalDate,
    onDateClicked: (DailyProgressUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    WeekRow(modifier = modifier) {
        progressByDate.forEach { progressByDate ->
            CircleProgressBar(
                date = progressByDate.displayedDate,
                status = progressByDate.status,
                onClick = { onDateClicked(progressByDate) },
                isSelected = progressByDate.actualDate == selectedDate,
                isEnabled = progressByDate.status != ProgressUiState.NotInProgress,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun GoalStartScreenPreview() {
    GoalMateTheme {
        GoalMateCalendar(
            calendarData = CalendarUiModel.DUMMY,
            selectedDate = LocalDate.of(2025, 2, 21),
            onAction = {},
        )
    }
}

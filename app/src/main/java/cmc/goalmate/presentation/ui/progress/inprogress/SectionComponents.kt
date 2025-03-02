package cmc.goalmate.presentation.ui.progress.inprogress

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.GoalMateProgressBar
import cmc.goalmate.presentation.components.MoreOptionButton
import cmc.goalmate.presentation.components.ThickDivider
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.progress.components.DailyTodoSectionSkeleton
import cmc.goalmate.presentation.ui.progress.components.GoalMateCalendar
import cmc.goalmate.presentation.ui.progress.components.GoalMateTimer
import cmc.goalmate.presentation.ui.progress.components.Subtitle
import cmc.goalmate.presentation.ui.progress.components.ToDoItem
import cmc.goalmate.presentation.ui.progress.components.TodayProgress
import cmc.goalmate.presentation.ui.progress.inprogress.model.CalendarUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.DailyProgressDetailUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.GoalOverViewUiModel
import cmc.goalmate.presentation.ui.progress.inprogress.model.UiState
import java.time.LocalDate

@Composable
fun CalendarSection(
    weeklyProgressState: UiState<CalendarUiModel>,
    selectedDate: LocalDate,
    onAction: (InProgressAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (weeklyProgressState) {
        is UiState.Success -> {
            GoalMateCalendar(
                calendarData = weeklyProgressState.data,
                selectedDate = selectedDate,
                onAction = onAction,
                modifier = modifier,
            )
        }
        is UiState.Error -> {}
        UiState.Loading -> {}
    }
}

@Composable
fun DailyTodoSection(
    dailyProgressState: UiState<DailyProgressDetailUiModel>,
    onAction: (InProgressAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (dailyProgressState) {
        is UiState.Error -> {}
        UiState.Loading -> {
            DailyTodoSectionSkeleton()
        }
        is UiState.Success -> {
            val dailyProgress = dailyProgressState.data
            Column(modifier = modifier) {
                GoalTasks(
                    dailyProgress = dailyProgress,
                    onAction = onAction,
                    modifier = Modifier
                        .background(MaterialTheme.goalMateColors.thickDivider),
                )

                Spacer(Modifier.size(GoalMateDimens.VerticalSpacerLarge))

                ProgressLayout(
                    title = "오늘 진척률",
                    progressContent = {
                        TodayProgress(
                            actualPercent = dailyProgress.actualProgress,
                        )
                    },
                    modifier = Modifier.padding(horizontal = GoalMateDimens.HorizontalPadding),
                )
            }
        }
    }
}

@Composable
private fun GoalTasks(
    dailyProgress: DailyProgressDetailUiModel,
    onAction: (InProgressAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(
            vertical = GoalMateDimens.ItemVerticalPaddingLarge,
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = GoalMateDimens.HorizontalPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Subtitle(title = "오늘 해야 할 일")
            if (dailyProgress.showTimer) {
                GoalMateTimer(
                    timerStatus = dailyProgress.timerStatus,
                    modifier = Modifier.width(136.dp),
                )
            }
        }

        Spacer(Modifier.size(18.dp))

        dailyProgress.todos.forEachIndexed { index, todo ->
            ToDoItem(
                todo = todo,
                onCheckedChange = {
                    onAction(
                        InProgressAction.CheckTodo(
                            todoId = todo.id,
                            currentState = todo.isCompleted,
                        ),
                    )
                },
                modifier = Modifier.padding(start = 8.dp, end = 20.dp),
            )
            if (index != dailyProgress.todos.lastIndex) {
                Spacer(Modifier.size(7.dp))
            }
        }
    }
}

@Composable
fun GoalInfoSection(
    goalInfoState: UiState<GoalOverViewUiModel>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (goalInfoState) {
        is UiState.Error -> {
            Log.d("yenny", "GoalInfoSection error")
        }

        UiState.Loading -> {}
        is UiState.Success -> {
            val goalInfo = goalInfoState.data
            Column(modifier = modifier) {
                ProgressLayout(
                    title = "전체 진척률",
                    progressContent = {
                        GoalMateProgressBar(
                            currentProgress = goalInfo.totalProgress,
                            thickness = 20.dp,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    },
                    modifier = Modifier.padding(horizontal = GoalMateDimens.HorizontalPadding),
                )

                ThickDivider(paddingTop = 40.dp, paddingBottom = 30.dp)

                GoalInfoDetail(
                    goalInfo = goalInfo,
                    onDetailButtonClicked = { navigateToDetail(goalInfo.goalId) },
                    modifier = Modifier.padding(horizontal = GoalMateDimens.HorizontalPadding)
                        .padding(bottom = GoalMateDimens.VerticalSpacerLarge),
                )
            }
        }
    }
}

@Composable
private fun ProgressLayout(
    title: String,
    progressContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
    ) {
        Subtitle(
            title = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )
        progressContent()
    }
}

@Composable
fun GoalInfoDetail(
    goalInfo: GoalOverViewUiModel,
    onDetailButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        MoreOptionButton(
            label = stringResource(R.string.goal_more_detail_button),
            onClick = onDetailButtonClicked,
            modifier = Modifier.align(Alignment.End),
        )

        val cellPaddingModifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        val cellTextStyle = MaterialTheme.goalMateTypography.bodySmallMedium

        Column(
            modifier = Modifier.border(
                width = 1.dp,
                color = MaterialTheme.goalMateColors.surface,
                shape = RoundedCornerShape(6.dp),
            ),
        ) {
            listOf(
                stringResource(R.string.goal_detail_start_title) to goalInfo.title,
                stringResource(R.string.goal_detail_start_mentor_title) to goalInfo.mentor,
            ).forEach { (label, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                ) {
                    Text(
                        text = label,
                        modifier = cellPaddingModifier,
                        style = cellTextStyle,
                        color = MaterialTheme.goalMateColors.labelTitle,
                    )
                    VerticalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.goalMateColors.surface,
                        modifier = Modifier.fillMaxHeight(),
                    )
                    Text(
                        text = value,
                        modifier = cellPaddingModifier,
                        style = cellTextStyle,
                        color = MaterialTheme.goalMateColors.onSurfaceVariant,
                    )
                }

                if (label == stringResource(R.string.goal_detail_start_title)) {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.goalMateColors.surface,
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun MyGoalProgressContentPreview() {
    GoalMateTheme {
        DailyTodoSection(
            dailyProgressState = UiState.Success(DailyProgressDetailUiModel.DUMMY),
            onAction = {},
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun MyGoalProgressContentLoadingPreview() {
    GoalMateTheme {
        DailyTodoSection(
            dailyProgressState = UiState.Loading,
            onAction = {},
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun GoalInfoDetailPreview() {
    GoalMateTheme {
        GoalInfoSection(
            goalInfoState = UiState.Success(GoalOverViewUiModel.DUMMY),
            {},
        )
    }
}

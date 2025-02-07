package cmc.goalmate.presentation.ui.progress.inprogress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.app.navigation.NavigateToGoal
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.components.GoalMateProgressBar
import cmc.goalmate.presentation.components.MoreOptionButton
import cmc.goalmate.presentation.components.ThickDivider
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.White
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.mygoals.MyGoalState
import cmc.goalmate.presentation.ui.progress.components.GoalMateCalendar
import cmc.goalmate.presentation.ui.progress.components.GoalMateTimer
import cmc.goalmate.presentation.ui.progress.components.Subtitle
import cmc.goalmate.presentation.ui.progress.components.TimerStatus
import cmc.goalmate.presentation.ui.progress.components.ToDoItem
import cmc.goalmate.presentation.ui.progress.components.TodayProgress
import cmc.goalmate.presentation.ui.progress.inprogress.model.TodoGoalUiModel
import java.time.YearMonth

@Composable
fun InProgressContent(
    state: InProgressUiState,
    onAction: (InProgressAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(
                    top = 6.dp,
                    bottom = GoalMateDimens.ItemVerticalPaddingLarge,
                )
                .verticalScroll(rememberScrollState()),
        ) {
            GoalProgress(
                state = state,
                onAction = onAction,
            )
            ThickDivider(paddingTop = 40.dp, paddingBottom = 30.dp)
            GoalInfoDetail(
                goalInfo = state.goalInfo,
                navigateToGoalDetail = { onAction(InProgressAction.NavigateToGoalDetail(state.goalInfo.goalId)) },
                modifier = modifier.padding(horizontal = GoalMateDimens.HorizontalPadding),
            )
            Spacer(Modifier.size(86.dp))
        }

        GoalMateButton(
            content = "멘토 코멘트 받으러 가기",
            onClick = { onAction(InProgressAction.NavigateToComments(state.goalInfo.goalId)) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    horizontal = GoalMateDimens.HorizontalPadding,
                    vertical = GoalMateDimens.BottomMargin,
                ),
        )
    }
}

@Composable
private fun GoalProgress(
    state: InProgressUiState,
    onAction: (InProgressAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val canModifyTodo = state.canModifyTodoCheck(YearMonth.of(2025, 1), 24) // TODO: 추후 오늘 날짜로 변경\

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(GoalMateDimens.ItemVerticalPaddingLarge),
    ) {
        GoalMateCalendar(
            calendarData = state.weeklyData,
            selectedDate = state.selectedDate.date,
            onAction = onAction,
            modifier = Modifier.padding(horizontal = GoalMateDimens.HorizontalPadding),
        )

        GoalTasks(
            todos = state.todos,
            isEnabled = canModifyTodo,
            onTodoCheckedChange = { todoId, updatedChecked ->
                if (canModifyTodo) {
                    onAction(InProgressAction.CheckTodo(todoId, updatedChecked))
                } else {
                    onAction(InProgressAction.ClickUneditableGoal)
                }
            },
            modifier = Modifier
                .background(MaterialTheme.goalMateColors.thickDivider)
                .padding(horizontal = GoalMateDimens.HorizontalPadding),
        )

        AchievementProgress(
            currentProgressPercentage = state.currentAchievementRate,
            totalProgressPercentage = 20f, // TODO: 전체 진행률 고민중
            modifier = Modifier
                .padding(horizontal = GoalMateDimens.HorizontalPadding)
                .padding(bottom = GoalMateDimens.ItemVerticalPaddingLarge),
        )
    }
}

@Composable
private fun GoalTasks(
    todos: List<TodoGoalUiModel>,
    isEnabled: Boolean,
    onTodoCheckedChange: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(vertical = GoalMateDimens.ItemVerticalPaddingLarge),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Subtitle(title = "오늘 해야 할 일")
            GoalMateTimer(
                time = "23:00:00",
                timerStatus = TimerStatus.RUNNING,
                modifier = Modifier.width(136.dp),
            )
        }

        Spacer(Modifier.size(GoalMateDimens.ItemVerticalPaddingLarge))

        todos.forEachIndexed { index, todo ->
            ToDoItem(
                todo = todo,
                isEnabled = isEnabled,
                onCheckedChange = { onTodoCheckedChange(todo.id, !todo.isCompleted) },
            )
            if (index != todos.lastIndex) {
                Spacer(Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun AchievementProgress(
    currentProgressPercentage: Int,
    totalProgressPercentage: Float,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Subtitle(
            title = "오늘 진척률",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        )
        TodayProgress(
            progressPercent = currentProgressPercentage,
        )

        Spacer(Modifier.size(30.dp))

        Subtitle(
            title = "전체 진척률",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )
        GoalMateProgressBar(
            currentProgress = totalProgressPercentage,
            myGoalState = MyGoalState.IN_PROGRESS,
            thickness = 20.dp,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun GoalInfoDetail(
    goalInfo: GoalOverViewUiModel,
    navigateToGoalDetail: NavigateToGoal,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        MoreOptionButton(
            label = stringResource(R.string.goal_more_detail_button),
            onClick = { navigateToGoalDetail(goalInfo.goalId) },
            modifier = Modifier.align(Alignment.End),
        )

        val cellPaddingModifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        val cellTextStyle = MaterialTheme.goalMateTypography.bodySmallMedium

        Column {
            listOf(
                stringResource(R.string.goal_detail_start_title) to goalInfo.title,
                stringResource(R.string.goal_detail_start_mentor_title) to goalInfo.mentor,
            ).forEach { (label, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = label,
                        modifier = cellPaddingModifier,
                        style = cellTextStyle,
                        color = MaterialTheme.goalMateColors.labelTitle,
                    )
                    Text(
                        text = value,
                        modifier = cellPaddingModifier,
                        style = cellTextStyle,
                        color = MaterialTheme.goalMateColors.onSurfaceVariant,
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
        InProgressContent(
            state = InProgressUiState.initialInProgressUiState(),
            onAction = {},
            modifier = Modifier.background(White),
        )
    }
}

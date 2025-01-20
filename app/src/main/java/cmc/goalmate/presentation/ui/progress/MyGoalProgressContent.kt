package cmc.goalmate.presentation.ui.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.components.GoalMateCheckBoxWithText
import cmc.goalmate.presentation.components.GoalMateProgressBar
import cmc.goalmate.presentation.components.MoreOptionButton
import cmc.goalmate.presentation.components.ThickDivider
import cmc.goalmate.presentation.components.ThinDivider
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.White
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.mygoals.MyGoalState
import cmc.goalmate.presentation.ui.progress.components.CommentPreview
import cmc.goalmate.presentation.ui.progress.components.CommentUiModel
import cmc.goalmate.presentation.ui.progress.components.GoalMateCalendar
import cmc.goalmate.presentation.ui.progress.components.TodayProgress
import cmc.goalmate.presentation.ui.progress.model.CalendarUiModel

data class TodoGoalUiModel(val content: String, val isCompleted: Boolean)

data class GoalProgressUiModel(
    val todos: List<TodoGoalUiModel>,
) {
    companion object {
        val DUMMY = GoalProgressUiModel(
            todos = listOf(
                TodoGoalUiModel("영어 단어 보카 30개 암기", false),
                TodoGoalUiModel("영어 신문 읽기 1쪽", true),
            ),
        )
    }
}

@Composable
fun MyGoalProgressContent(modifier: Modifier = Modifier) {
    Column {
        Column(
            modifier = modifier
                .padding(horizontal = GoalMateDimens.HorizontalPadding),
        ) {
            GoalMateCalendar(calendarUiModel = CalendarUiModel.DUMMY)

            Spacer(Modifier.size(45.dp))

            GoalTasks(todos = GoalProgressUiModel.DUMMY.todos)

            ThinDivider(paddingBottom = 30.dp)

            AchievementProgress()

            ThinDivider(paddingTop = 30.dp, paddingBottom = 30.dp)

            CommentSection()
        }
        ThickDivider(paddingTop = 40.dp, paddingBottom = 30.dp)
    }
}

@Composable
private fun Subtitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = MaterialTheme.goalMateTypography.subtitleSmall,
        color = MaterialTheme.goalMateColors.onSecondary02,
        modifier = modifier,
    )
}

@Composable
private fun GoalTasks(
    todos: List<TodoGoalUiModel>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Subtitle(title = "오늘 해야 할 일")
        Spacer(Modifier.size(16.dp))
        ThinDivider()
        Spacer(Modifier.size(30.dp))
        todos.forEach { todo ->
            GoalMateCheckBoxWithText(
                content = todo.content,
                isChecked = todo.isCompleted,
                onCheckedChange = {},
            )
            Spacer(Modifier.size(16.dp))
        }
        Spacer(Modifier.size(30.dp))
    }
}

@Composable
private fun AchievementProgress(modifier: Modifier = Modifier) {
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
            progressPercent = 20f,
        )
        Spacer(Modifier.size(30.dp))

        Subtitle(
            title = "전체 진척률",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )
        GoalMateProgressBar(
            currentProgress = 0.2f,
            myGoalState = MyGoalState.IN_PROGRESS,
            thickness = 20.dp,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun CommentSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Subtitle(title = "멘토 코멘트")
            MoreOptionButton("전체 보기", onClick = {})
        }
        Spacer(Modifier.size(16.dp))
        CommentPreview(
            comment = CommentUiModel.DUMMY,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun MyGoalProgressContentPreview() {
    GoalMateTheme {
        MyGoalProgressContent(
            modifier = Modifier.background(White),
        )
    }
}

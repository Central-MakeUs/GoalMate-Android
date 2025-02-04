package cmc.goalmate.presentation.ui.mygoals

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.goalMateColors

data class MyGoalUiModel(
    val goalId: Long,
    val title: String,
    val mentorName: String,
    val startDate: String,
    val endDate: String,
    val daysFromStart: Int,
    val goalProgress: Float,
    val goalState: MyGoalState,
    val remainGoals: Int = 0,
) {
    companion object {
        val DUMMY = MyGoalUiModel(
            goalId = 0,
            title = "다온과 함께하는 영어 완전 정복 30일 목표",
            mentorName = "다온",
            startDate = "2025년 01월 01일 부터",
            endDate = "2025년 01월 30일까지",
            daysFromStart = 2,
            goalProgress = 0.2f,
            goalState = MyGoalState.IN_PROGRESS,
        )
        val DUMMY2 = MyGoalUiModel(
            goalId = 0,
            title = "마루와 함께하는 백앤드 서버 찐천재 목표",
            mentorName = "마루",
            startDate = "2025년 01월 01일 부터",
            endDate = "2025년 01월 30일까지",
            daysFromStart = 2,
            goalProgress = 0.2f,
            goalState = MyGoalState.COMPLETED,
        )
    }
}

enum class MyGoalState(
    val label: String,
    private val dateFormat: String,
) {
    IN_PROGRESS(label = "진행중", dateFormat = "D-%s"),
    COMPLETED(label = "진행완료", dateFormat = "done"),
    ;

    fun getDateTag(daysUntilDeadline: Int): String = dateFormat.format(daysUntilDeadline)

    companion object {
        @Composable
        fun MyGoalState.dateIcon(): Int =
            when (this) {
                IN_PROGRESS -> R.drawable.icon_calendar_active
                COMPLETED -> R.drawable.icon_calendar_inactive
            }

        @Composable
        fun MyGoalState.progressIcon(): Int =
            when (this) {
                IN_PROGRESS -> R.drawable.icon_progress_active
                COMPLETED -> R.drawable.icon_progress_inactive
            }

        @Composable
        fun MyGoalState.textColor(): Color =
            when (this) {
                IN_PROGRESS -> MaterialTheme.goalMateColors.onBackground
                COMPLETED -> MaterialTheme.goalMateColors.finished
            }

        @Composable
        fun MyGoalState.tagColor(): Color =
            when (this) {
                IN_PROGRESS -> MaterialTheme.goalMateColors.secondary02
                COMPLETED -> MaterialTheme.goalMateColors.secondary01
            }

        @Composable
        fun MyGoalState.onTagColor(): Color =
            when (this) {
                IN_PROGRESS -> MaterialTheme.goalMateColors.onSecondary02
                COMPLETED -> MaterialTheme.goalMateColors.onSecondary
            }

        @Composable
        fun MyGoalState.progressIndicatorColor(): Color =
            when (this) {
                IN_PROGRESS -> MaterialTheme.goalMateColors.activeProgressBar
                COMPLETED -> MaterialTheme.goalMateColors.completedProgressBar
            }

        @Composable
        fun MyGoalState.progressBackgroundColor(): Color =
            when (this) {
                IN_PROGRESS -> MaterialTheme.goalMateColors.activeProgressBackground
                COMPLETED -> MaterialTheme.goalMateColors.completedProgressBackground
            }
    }
}

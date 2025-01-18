package cmc.goalmate.presentation.ui.mygoals

import androidx.annotation.DrawableRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.goalMateColors

data class MyGoalUiModel(
    val title: String,
    val mentorName: String,
    val startDate: String,
    val endDate: String,
    val daysFromStart: Int,
    val goalProgress: Float,
    val goalState: MyGoalState,
) {
    companion object {
        val DUMMY = MyGoalUiModel(
            title = "다온과 함께하는 영어 완전 정복 30일 목표",
            mentorName = "다온",
            startDate = "2025년 01월 01일 부터",
            endDate = "2025년 01월 30일까지",
            daysFromStart = 2,
            goalProgress = 0.2f,
            goalState = MyGoalState.IN_PROGRESS,
        )
    }
}

enum class MyGoalState(
    val label: String,
    @DrawableRes val progressIcon: Int,
    @DrawableRes val dateIcon: Int,
) {
    IN_PROGRESS(
        label = "진행중",
        progressIcon = R.drawable.icon_progress_active,
        dateIcon = R.drawable.icon_calendar_active,
    ),
    COMPLETED(
        label = "진행완료",
        progressIcon = R.drawable.icon_progress_inactive,
        dateIcon = R.drawable.icon_calendar_inactive,
    ),
    ;

    companion object {
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

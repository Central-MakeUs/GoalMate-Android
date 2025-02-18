package cmc.goalmate.presentation.ui.mygoals

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.ui.mygoals.MyGoalState.COMPLETED
import cmc.goalmate.presentation.ui.mygoals.MyGoalState.IN_PROGRESS

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

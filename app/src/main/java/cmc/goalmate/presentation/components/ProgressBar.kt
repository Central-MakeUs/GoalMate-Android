package cmc.goalmate.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.mygoals.MyGoalUiState
import cmc.goalmate.presentation.ui.mygoals.progressBackgroundColor
import cmc.goalmate.presentation.ui.mygoals.progressIndicatorColor

@Composable
fun GoalMateProgressBar(
    currentProgress: Float,
    thickness: Dp,
    modifier: Modifier = Modifier,
    myGoalState: MyGoalUiState = MyGoalUiState.IN_PROGRESS,
) {
    Column(modifier = modifier) {
        LinearProgressIndicator(
            progress = { currentProgress },
            color = myGoalState.progressIndicatorColor(),
            modifier = Modifier
                .fillMaxWidth()
                .height(thickness),
            strokeCap = StrokeCap.Round,
            gapSize = (-15).dp,
            trackColor = myGoalState.progressBackgroundColor(),
            drawStopIndicator = {},
        )
        Spacer(Modifier.size(8.dp))
        Text(
            text = "${(currentProgress * 100).toInt()}%",
            style = MaterialTheme.goalMateTypography.bodySmallMedium,
            color = MaterialTheme.goalMateColors.onSurfaceVariant,
            modifier = Modifier.align(alignment = Alignment.End),
        )
    }
}

@Composable
@Preview
private fun GoalMateProgressBarPreview() {
    GoalMateTheme {
        GoalMateProgressBar(
            currentProgress = 0.7f,
            myGoalState = MyGoalUiState.IN_PROGRESS,
            thickness = 14.dp,
        )
    }
}

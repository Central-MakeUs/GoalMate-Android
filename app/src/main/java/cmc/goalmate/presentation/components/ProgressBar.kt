package cmc.goalmate.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.mygoals.MyGoalState
import cmc.goalmate.presentation.ui.mygoals.MyGoalState.Companion.progressBackgroundColor
import cmc.goalmate.presentation.ui.mygoals.MyGoalState.Companion.progressIndicatorColor

@Composable
fun GoalMateProgressBar(
    currentProgress: Float,
    myGoalState: MyGoalState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        LinearProgressIndicator(
            progress = { currentProgress },
            color = myGoalState.progressIndicatorColor(),
            modifier = Modifier.height(14.dp).clip(RoundedCornerShape(14.dp)),
            trackColor = myGoalState.progressBackgroundColor(),
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
            myGoalState = MyGoalState.IN_PROGRESS,
        )
    }
}

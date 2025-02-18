package cmc.goalmate.presentation.ui.mygoals.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.components.TextTag
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.mygoals.MyGoalState
import cmc.goalmate.presentation.ui.mygoals.onTagColor
import cmc.goalmate.presentation.ui.mygoals.tagColor
import cmc.goalmate.presentation.ui.mygoals.textColor

@Composable
fun GoalStatusTag(
    daysFromStart: Int,
    goalState: MyGoalState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = goalState.label,
            style = MaterialTheme.goalMateTypography.captionMedium,
            color = goalState.textColor(),
        )
        Spacer(Modifier.size(4.dp))
        TextTag(
            text = goalState.getDateTag(daysFromStart),
            textColor = goalState.onTagColor(),
            backgroundColor = goalState.tagColor(),
            textStyle = MaterialTheme.goalMateTypography.captionSemiBold,
        )
    }
}

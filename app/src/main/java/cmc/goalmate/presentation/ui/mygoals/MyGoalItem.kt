package cmc.goalmate.presentation.ui.mygoals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.ButtonSize
import cmc.goalmate.presentation.components.GoalDateRange
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.components.GoalMateImage
import cmc.goalmate.presentation.components.GoalMateProgressBar
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.mygoals.MyGoalState.Companion.onTagColor
import cmc.goalmate.presentation.ui.mygoals.MyGoalState.Companion.tagColor
import cmc.goalmate.presentation.ui.mygoals.MyGoalState.Companion.textColor

@Composable
fun MyGoalItem(
    myGoal: MyGoalUiModel,
    navigateToProgressPage: GoalNavigation,
    navigateToCompletedGoalPage: GoalNavigation,
    navigateToGoalDetail: GoalNavigation,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = GoalMateDimens.HorizontalPadding)
            .padding(top = 24.dp, bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Label(
            daysFromStart = myGoal.daysFromStart,
            goalState = myGoal.goalState,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.size(14.dp))

        GoalOverview(myGoal)

        ProgressBar(
            myGoalState = myGoal.goalState,
            currentProgress = myGoal.goalProgress,
            modifier = Modifier.padding(vertical = 16.dp),
        )

        when (myGoal.goalState) {
            MyGoalState.IN_PROGRESS -> InProgressButton(
                navigateToProgressPage = {},
                modifier = Modifier,
            )

            MyGoalState.COMPLETED -> CompletedButtons(
                navigateToGoalDetail = {},
                navigateToCompletedGoalPage = {},
                modifier = Modifier,
            )
        }
    }
}

@Composable
private fun GoalOverview(
    myGoal: MyGoalUiModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier.size(width = 123.dp, height = 91.dp),
            contentAlignment = Alignment.Center,
        ) {
            GoalMateImage(
                image = R.drawable.goal_default_image,
                modifier = Modifier.matchParentSize(),
            )
            if (myGoal.goalState == MyGoalState.COMPLETED) {
                GoalMateImage(
                    image = R.drawable.image_completed,
                    modifier = Modifier.matchParentSize(),
                )
            }
        }

        Spacer(Modifier.size(10.dp))
        Column {
            Text(
                text = myGoal.title,
                style = MaterialTheme.goalMateTypography.buttonLabelLarge,
                color = myGoal.goalState.textColor(),
                modifier = Modifier.padding(bottom = 5.dp),
            )
            GoalDateRange(
                startDate = myGoal.startDate,
                endDate = myGoal.endDate,
                icon = myGoal.goalState.dateIcon,
            )
        }
    }
}

@Composable
private fun Label(
    daysFromStart: Int,
    goalState: MyGoalState,
    modifier: Modifier = Modifier,
) {
    val tagText = when (goalState) {
        MyGoalState.IN_PROGRESS -> "D+$daysFromStart"
        MyGoalState.COMPLETED -> "done"
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = goalState.label,
            style = MaterialTheme.goalMateTypography.captionSemiBold,
            color = goalState.textColor(),
        )
        Spacer(Modifier.size(4.dp))
        Text(
            text = tagText,
            style = MaterialTheme.goalMateTypography.captionSemiBold,
            color = goalState.onTagColor(),
            modifier = Modifier.background(
                color = goalState.tagColor(),
                shape = RoundedCornerShape(4.dp),
            ).padding(vertical = 2.dp, horizontal = 4.dp),
        )
    }
}

@Composable
private fun ProgressBar(
    myGoalState: MyGoalState,
    currentProgress: Float,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Text(
            text = "전체 진척율",
            style = MaterialTheme.goalMateTypography.captionSemiBold,
            color = MaterialTheme.goalMateColors.finished,
            modifier = Modifier.padding(end = 2.dp),
        )
        GoalMateImage(image = myGoalState.progressIcon, modifier = Modifier.size(16.dp))
        Spacer(Modifier.weight(1f))
        GoalMateProgressBar(
            currentProgress = currentProgress,
            myGoalState = myGoalState,
        )
    }
}

@Composable
private fun InProgressButton(
    navigateToProgressPage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GoalMateButton(
        content = stringResource(R.string.my_goals_in_progress_button),
        onClick = navigateToProgressPage,
        buttonSize = ButtonSize.SMALL,
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
private fun CompletedButtons(
    navigateToCompletedGoalPage: () -> Unit,
    navigateToGoalDetail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GoalMateButton(
            content = stringResource(R.string.my_goals_completed_restart_button),
            onClick = navigateToGoalDetail,
            hasOutLine = true,
            modifier = Modifier.weight(1f),
            buttonSize = ButtonSize.SMALL,
        )
        Spacer(Modifier.size(12.dp))
        GoalMateButton(
            content = stringResource(R.string.my_goals_completed_detail_button),
            onClick = navigateToCompletedGoalPage,
            modifier = Modifier.weight(1f),
            buttonSize = ButtonSize.SMALL,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun MyGoalItemActivePreview() {
    GoalMateTheme {
        MyGoalItem(
            myGoal = MyGoalUiModel.DUMMY,
            {},
            {},
            {},
            modifier = Modifier.background(Color.White),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun MyGoalItemCompletedPreview() {
    GoalMateTheme {
        MyGoalItem(
            myGoal = MyGoalUiModel.DUMMY.copy(goalState = MyGoalState.COMPLETED),
            {},
            {},
            {},
            modifier = Modifier.background(Color.White),
        )
    }
}

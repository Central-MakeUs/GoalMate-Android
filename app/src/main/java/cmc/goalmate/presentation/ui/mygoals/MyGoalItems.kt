package cmc.goalmate.presentation.ui.mygoals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.draw.alpha
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
import cmc.goalmate.presentation.ui.mygoals.components.GoalStatusTag

@Composable
private fun MyGoalItemLayout(
    myGoal: MyGoalUiModel,
    buttonContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = GoalMateDimens.HorizontalPadding)
            .padding(top = 24.dp, bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        GoalStatusTag(
            daysFromStart = myGoal.remainingDays,
            goalState = myGoal.goalState,
            modifier = Modifier.fillMaxWidth(),
        )

        GoalOverview(
            myGoal = myGoal,
            modifier = Modifier.fillMaxWidth(),
        )

        ProgressBar(
            myGoalState = myGoal.goalState,
            currentProgress = myGoal.goalProgress,
            modifier = Modifier,
        )

        buttonContent()
    }
}

@Composable
private fun GoalOverview(
    myGoal: MyGoalUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Thumbnail(
            myGoal = myGoal,
            modifier = Modifier,
        )

        Spacer(Modifier.size(10.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = myGoal.title,
                style = MaterialTheme.goalMateTypography.buttonLabelLarge,
                color = myGoal.goalState.textColor(),
                modifier = Modifier,
            )
            Spacer(Modifier.size(5.dp))
            GoalDateRange(
                startDate = myGoal.startDate,
                endDate = myGoal.endDate,
                icon = myGoal.goalState.dateIcon(),
                textStyle = MaterialTheme.goalMateTypography.labelSmall,
            )
        }
    }
}

@Composable
private fun Thumbnail(
    myGoal: MyGoalUiModel,
    modifier: Modifier = Modifier,
) {
    val imageModifier = Modifier.run {
        if (myGoal.goalState == MyGoalUiState.COMPLETED) {
            this.alpha(0.2f)
        } else {
            this
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        GoalMateImage(
            image = myGoal.thumbnailUrl,
            modifier = imageModifier.size(width = 123.dp, height = 91.dp),
            shape = RoundedCornerShape(4.dp),
        )
        if (myGoal.goalState == MyGoalUiState.COMPLETED) {
            GoalMateImage(
                image = R.drawable.image_completed,
                modifier = Modifier.size(width = 123.dp, height = 91.dp),
            )
        }
    }
}

@Composable
private fun ProgressBar(
    myGoalState: MyGoalUiState,
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
        GoalMateImage(image = myGoalState.progressIcon(), modifier = Modifier.size(16.dp))
        Spacer(Modifier.size(8.dp))
        GoalMateProgressBar(
            currentProgress = currentProgress,
            myGoalState = myGoalState,
            thickness = 14.dp,
        )
    }
}

@Composable
fun InProgressGoalItem(
    myGoal: MyGoalUiModel,
    onStartButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val buttonText = if (myGoal.remainGoals > 0) {
        stringResource(R.string.my_goals_in_progress_start_button, myGoal.remainGoals)
    } else {
        stringResource(R.string.my_goals_in_progress_button)
    }

    MyGoalItemLayout(
        myGoal = myGoal,
        buttonContent = {
            GoalMateButton(
                content = buttonText,
                onClick = onStartButtonClicked,
                buttonSize = ButtonSize.SMALL,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        modifier = modifier,
    )
}

@Composable
fun CompletedGoalItem(
    myGoal: MyGoalUiModel,
    onRestartButtonClicked: () -> Unit,
    onCompletedButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyGoalItemLayout(
        myGoal = myGoal,
        buttonContent = {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                GoalMateButton(
                    content = stringResource(R.string.my_goals_completed_restart_button),
                    onClick = onRestartButtonClicked,
                    hasOutLine = true,
                    modifier = Modifier.weight(1f),
                    buttonSize = ButtonSize.SMALL,
                )
                Spacer(Modifier.size(12.dp))
                GoalMateButton(
                    content = stringResource(R.string.my_goals_completed_detail_button),
                    onClick = onCompletedButtonClicked,
                    modifier = Modifier.weight(1f),
                    buttonSize = ButtonSize.SMALL,
                )
            }
        },
    )
}

@Composable
@Preview(showBackground = true)
private fun MyGoalItemActivePreview() {
    GoalMateTheme {
        InProgressGoalItem(
            myGoal = MyGoalUiModel.DUMMY.copy(goalState = MyGoalUiState.IN_PROGRESS),
            {},
            modifier = Modifier.background(Color.Blue),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun MyGoalItemCompletedPreview() {
    GoalMateTheme {
        CompletedGoalItem(
            myGoal = MyGoalUiModel.DUMMY.copy(goalState = MyGoalUiState.COMPLETED),
            {},
            {},
            modifier = Modifier.background(Color.White),
        )
    }
}

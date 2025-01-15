package cmc.goalmate.presentation.ui.auth.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.Grey300
import cmc.goalmate.presentation.theme.color.Grey400
import cmc.goalmate.presentation.theme.color.Grey600
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.auth.LoginStep
import cmc.goalmate.presentation.ui.auth.StepStatus

data class Step(val step: LoginStep, val status: StepStatus = StepStatus.PENDING)

@Composable
fun StepProgressBar(
    steps: List<Step>,
    modifier: Modifier = Modifier,
) {
    Column {
        Spacer(modifier = Modifier.size(86.dp))
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            steps.forEach {
                StepItem(
                    loginStep = it.step,
                    stepStatus = it.status,
                )
            }
        }
    }
}

@Composable
fun StepItem(
    loginStep: LoginStep,
    stepStatus: StepStatus,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(modifier = modifier) {
        val (stepChip, startBar, endBar, label) = createRefs()
//        val startGuideline = createGuidelineFromStart(12.dp)
        val endGuideline = createGuidelineFromEnd(0.dp)

        if (!loginStep.isFirstStep()) {
            ProcessBar(
                processStatus = stepStatus.previous(),
                modifier = Modifier.constrainAs(startBar) {
                    start.linkTo(parent.start)
                    top.linkTo(stepChip.top)
                    end.linkTo(stepChip.start)
                    bottom.linkTo(stepChip.bottom)
                },
            )
        }
        StepChip(
            step = loginStep.step,
            processStatus = stepStatus,
            modifier = Modifier.constrainAs(stepChip) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            },
        )

        if (!loginStep.isLastStep()) {
            ProcessBar(
                processStatus = stepStatus,
                modifier = Modifier.constrainAs(endBar) {
                    start.linkTo(stepChip.end)
                    top.linkTo(stepChip.top)
                    bottom.linkTo(stepChip.bottom)
                    end.linkTo(endGuideline)
                },
            )
        }

        if (stepStatus == StepStatus.CURRENT) {
            Label(
                content = loginStep.title,
                modifier = Modifier.constrainAs(label) {
                    top.linkTo(stepChip.bottom, margin = 8.dp)
                    centerHorizontallyTo(parent)
                },
            )
        }
    }
}

@Composable
private fun StepChip(
    step: String,
    processStatus: StepStatus,
    modifier: Modifier = Modifier,
) {
    val chipColor = when (processStatus) {
        StepStatus.CURRENT -> MaterialTheme.colorScheme.primary
        StepStatus.PENDING -> MaterialTheme.colorScheme.background
        StepStatus.COMPLETED -> MaterialTheme.goalMateColors.completed
    }
    val borderModifier = if (processStatus == StepStatus.PENDING) {
        modifier.border(width = 1.dp, color = Grey300, shape = CircleShape)
    } else {
        modifier
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = borderModifier
            .size(25.dp)
            .background(
                color = chipColor,
                shape = CircleShape,
            ),
    ) {
        if (processStatus == StepStatus.COMPLETED) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_check),
                contentDescription = null,
            )
        } else {
            val textColor = if (processStatus == StepStatus.PENDING) {
                Grey400
            } else {
                MaterialTheme.goalMateColors.onBackground
            }
            Text(
                text = step,
                style = MaterialTheme.goalMateTypography.buttonLabelSmall,
                color = textColor,
            )
        }
    }
}

@Composable
private fun ProcessBar(
    processStatus: StepStatus,
    modifier: Modifier = Modifier,
) {
    val processBarColor = when (processStatus) {
        StepStatus.COMPLETED -> MaterialTheme.goalMateColors.completed
        else -> MaterialTheme.goalMateColors.pending
    }
    Box(
        modifier = modifier
            .size(width = 25.dp, height = 8.dp)
            .background(
                color = processBarColor,
                shape = RectangleShape,
            ),
    )
}

@Composable
private fun Label(
    content: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = content,
        style = MaterialTheme.goalMateTypography.caption,
        color = Grey600,
        modifier = modifier,
    )
}

@Composable
@Preview(showBackground = true)
private fun StepProgressBarPreview() {
    val steps = listOf(
        Step(LoginStep.SIGN_UP, StepStatus.COMPLETED),
        Step(LoginStep.NICKNAME_SETTING, StepStatus.CURRENT),
        Step(LoginStep.COMPLETED, StepStatus.PENDING),
    )
    GoalMateTheme {
        StepProgressBar(steps = steps)
    }
}

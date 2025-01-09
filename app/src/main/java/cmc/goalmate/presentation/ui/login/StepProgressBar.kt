package cmc.goalmate.presentation.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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

@Composable
fun Step(
    process: LoginSteps,
    processStatus: ProcessStatus,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(modifier = modifier) {
        val (stepChip, startBar, endBar, label) = createRefs()

        if (!process.isFirstStep()) {
            ProcessBar(
                processStatus = processStatus.previous(),
                modifier = Modifier.constrainAs(startBar) {
                    start.linkTo(parent.start)
                    top.linkTo(stepChip.top)
                    end.linkTo(stepChip.start)
                    bottom.linkTo(stepChip.bottom)
                },
            )
        }
        StepChip(
            step = process.step,
            processStatus = processStatus,
            modifier = Modifier.constrainAs(stepChip) {
                start.linkTo(label.start)
                end.linkTo(label.end)
                top.linkTo(parent.top)
            },
        )

        if (!process.isLastStep()) {
            ProcessBar(
                processStatus = processStatus,
                modifier = Modifier.constrainAs(endBar) {
                    start.linkTo(stepChip.end)
                    top.linkTo(stepChip.top)
                    bottom.linkTo(stepChip.bottom)
                    end.linkTo(parent.end)
                },
            )
        }

        Label(
            content = process.title,
            modifier = Modifier.constrainAs(label) {
                top.linkTo(stepChip.bottom, margin = 8.dp)
            },
        )
    }
}

@Composable
private fun StepChip(
    step: String,
    processStatus: ProcessStatus,
    modifier: Modifier = Modifier,
) {
    val chipColor = when (processStatus) {
        ProcessStatus.CURRENT -> MaterialTheme.colorScheme.primary
        ProcessStatus.PENDING -> MaterialTheme.colorScheme.background
        ProcessStatus.COMPLETED -> MaterialTheme.goalMateColors.completed
    }
    val borderModifier = if (processStatus == ProcessStatus.PENDING) {
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
        if (processStatus == ProcessStatus.COMPLETED) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_check),
                contentDescription = null,
            )
        } else {
            val textColor = if (processStatus == ProcessStatus.PENDING) {
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
    processStatus: ProcessStatus,
    modifier: Modifier = Modifier,
) {
    val processBarColor = when (processStatus) {
        ProcessStatus.COMPLETED -> MaterialTheme.goalMateColors.completed
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
@Preview
fun StepProgressBarPreview() {
    GoalMateTheme {
        Row {
            Step(process = LoginSteps.SIGN_UP, processStatus = ProcessStatus.COMPLETED)
            Step(process = LoginSteps.NICKNAME_SETTING, processStatus = ProcessStatus.CURRENT)
            Step(process = LoginSteps.COMPLETED, processStatus = ProcessStatus.PENDING)
        }
    }
}

enum class LoginSteps(val step: String, val title: String) {
    SIGN_UP("1", "회원가입"),
    NICKNAME_SETTING("2", "닉네임 설정"),
    COMPLETED("3", "시작하기"),
    ;

    fun isFirstStep(): Boolean = this == SIGN_UP

    fun isLastStep(): Boolean = this == COMPLETED
}

enum class ProcessStatus {
    CURRENT,
    PENDING,
    COMPLETED,
    ;

    fun previous(): ProcessStatus =
        when (this) {
            CURRENT -> COMPLETED
            PENDING -> PENDING
            COMPLETED -> COMPLETED
        }
}

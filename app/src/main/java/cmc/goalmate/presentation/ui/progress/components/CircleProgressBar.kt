package cmc.goalmate.presentation.ui.progress.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.progress.inprogress.model.ProgressStatus

private const val START_ANGLE = 270f
const val CIRCLE_SIZE = 30

@Composable
fun CircleProgressBar(
    date: Int,
    status: ProgressStatus,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    isEnabled: Boolean = true,
) {
    Box(
        modifier = modifier
            .size(CIRCLE_SIZE.dp)
            .clip(CircleShape)
            .clickable(
                enabled = isEnabled,
                onClick = { onClick(date) },
            )
            .background(status.backgroundColor(isSelected))
            .then(status.progress(isSelected)),
        contentAlignment = Alignment.Center,
    ) {
        status.centerContent(date = date, isSelected).invoke()
    }
}

@Composable
private fun ProgressStatus.textColor(isSelected: Boolean): Color =
    when (this) {
        ProgressStatus.InProgress -> MaterialTheme.goalMateColors.onBackground
        ProgressStatus.NotInProgress -> MaterialTheme.goalMateColors.disabled
        else -> {
            if (isSelected) {
                MaterialTheme.goalMateColors.onSecondary
            } else {
                MaterialTheme.goalMateColors.textButton
            }
        }
    }

@Composable
private fun ProgressStatus.backgroundColor(isSelected: Boolean): Color =
    when (this) {
        ProgressStatus.InProgress -> {
            if (isSelected) {
                MaterialTheme.goalMateColors.secondary02
            } else {
                MaterialTheme.goalMateColors.pending
            }
        }
        ProgressStatus.NotStart -> {
            if (isSelected) {
                MaterialTheme.goalMateColors.secondary01
            } else {
                Color.Transparent
            }
        }
        is ProgressStatus.Completed -> {
            if (isSelected) {
                MaterialTheme.goalMateColors.secondary01
            } else {
                MaterialTheme.goalMateColors.completed
            }
        }
        ProgressStatus.NotInProgress -> Color.Transparent
    }

@Composable
private fun ProgressStatus.centerContent(
    date: Int,
    isSelected: Boolean,
): @Composable () -> Unit =
    when (this) {
        ProgressStatus.InProgress -> {
            {
                TextContent(text = "$date", color = this.textColor(isSelected))
            }
        }

        ProgressStatus.NotStart -> {
            {
                TextContent(text = "$date", color = this.textColor(isSelected))
            }
        }

        is ProgressStatus.Completed -> {
            {
                when {
                    isSelected -> {
                        TextContent(
                            text = "$date",
                            color = this.textColor(true),
                        )
                    }
                    this.actualProgress == 100 -> {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.icon_checkbox_check),
                            contentDescription = null,
                        )
                    }
                }
            }
        }

        ProgressStatus.NotInProgress -> {
            {
                TextContent(text = "$date", color = this.textColor(isSelected))
            }
        }
    }

@Composable
private fun TextContent(
    text: String,
    color: Color,
) {
    Text(
        text = text,
        style = MaterialTheme.goalMateTypography.buttonLabelLarge,
        color = color,
        modifier = Modifier,
    )
}

@Composable
private fun ProgressStatus.progress(isSelected: Boolean): Modifier =
    if (this is ProgressStatus.Completed && !isSelected) {
        val progressColor = MaterialTheme.goalMateColors.primary
        val sweepAngle = this.displayProgress

        Modifier.drawBehind {
            drawArc(
                color = progressColor,
                startAngle = START_ANGLE,
                sweepAngle = sweepAngle,
                useCenter = true,
            )
        }
    } else {
        Modifier
    }

@Composable
@Preview
private fun CircleProgressBarPreview() {
    GoalMateTheme {
        CircleProgressBar(
            date = 10,
            status = ProgressStatus.Completed(10),
            onClick = {},
            isSelected = false,
        )
    }
}

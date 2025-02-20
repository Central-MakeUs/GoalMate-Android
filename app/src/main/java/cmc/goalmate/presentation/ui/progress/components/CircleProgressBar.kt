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
import cmc.goalmate.presentation.ui.progress.inprogress.model.ProgressUiState

private const val START_ANGLE = 270f
const val CIRCLE_SIZE = 30

@Composable
fun CircleProgressBar(
    date: Int,
    status: ProgressUiState,
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
private fun ProgressUiState.textColor(isSelected: Boolean): Color =
    when (this) {
        ProgressUiState.InProgress -> MaterialTheme.goalMateColors.onBackground
        ProgressUiState.NotInProgress -> MaterialTheme.goalMateColors.disabled
        else -> {
            if (isSelected) {
                MaterialTheme.goalMateColors.onSecondary
            } else {
                MaterialTheme.goalMateColors.textButton
            }
        }
    }

@Composable
private fun ProgressUiState.backgroundColor(isSelected: Boolean): Color =
    when (this) {
        ProgressUiState.InProgress -> {
            if (isSelected) {
                MaterialTheme.goalMateColors.secondary02
            } else {
                MaterialTheme.goalMateColors.pending
            }
        }
        ProgressUiState.NotStart -> {
            if (isSelected) {
                MaterialTheme.goalMateColors.secondary01
            } else {
                Color.Transparent
            }
        }
        is ProgressUiState.Completed -> {
            if (isSelected) {
                MaterialTheme.goalMateColors.secondary01
            } else {
                MaterialTheme.goalMateColors.completed
            }
        }
        ProgressUiState.NotInProgress -> Color.Transparent
    }

@Composable
private fun ProgressUiState.centerContent(
    date: Int,
    isSelected: Boolean,
): @Composable () -> Unit =
    when (this) {
        ProgressUiState.InProgress -> {
            {
                TextContent(text = "$date", color = this.textColor(isSelected))
            }
        }

        ProgressUiState.NotStart -> {
            {
                TextContent(text = "$date", color = this.textColor(isSelected))
            }
        }

        is ProgressUiState.Completed -> {
            {
                when {
                    isSelected -> {
                        TextContent(
                            text = "$date",
                            color = this.textColor(true),
                        )
                    }
                    this.actualProgress == 1f -> {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.icon_checkbox_check),
                            contentDescription = null,
                        )
                    }
                }
            }
        }

        ProgressUiState.NotInProgress -> {
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
        style = MaterialTheme.goalMateTypography.bodySmallMedium,
        color = color,
        modifier = Modifier,
    )
}

@Composable
private fun ProgressUiState.progress(isSelected: Boolean): Modifier =
    if (this is ProgressUiState.Completed && !isSelected) {
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
            status = ProgressUiState.Completed(0.5f),
            onClick = {},
            isSelected = false,
        )
    }
}

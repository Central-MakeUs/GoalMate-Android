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
import cmc.goalmate.presentation.ui.progress.model.ProgressStatus

private const val START_ANGLE = 270f
const val CIRCLE_SIZE = 30

@Composable
fun CircleProgressBar(
    date: Int,
    status: ProgressStatus,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {
    Box(
        modifier = modifier
            .size(CIRCLE_SIZE.dp)
            .clip(CircleShape)
            .clickable { onClick(date) }
            .background(status.backgroundColor())
            .then(status.progress()),
        contentAlignment = Alignment.Center,
    ) {
        status.centerContent(date = date).invoke()
    }
}

@Composable
private fun ProgressStatus.textColor(): Color =
    when (this) {
        ProgressStatus.InProgress -> MaterialTheme.goalMateColors.onBackground
        else -> MaterialTheme.goalMateColors.textButton
    }

@Composable
private fun ProgressStatus.backgroundColor(): Color =
    when (this) {
        ProgressStatus.InProgress -> MaterialTheme.goalMateColors.secondary02
        ProgressStatus.NotStart -> Color.Transparent
        is ProgressStatus.Completed -> MaterialTheme.goalMateColors.primaryVariant
    }

@Composable
private fun ProgressStatus.centerContent(date: Int): @Composable () -> Unit =
    when (this) {
        ProgressStatus.InProgress -> {
            {
                TextContent(text = "$date", color = this.textColor())
            }
        }

        ProgressStatus.NotStart -> {
            {
                TextContent(text = "$date", color = this.textColor())
            }
        }

        is ProgressStatus.Completed -> {
            {
                if (this.actualProgress == 100) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.icon_checkbox_check),
                        contentDescription = null,
                    )
                } else {
                    TextContent(text = "${this.actualProgress}", color = this.textColor())
                }
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
private fun ProgressStatus.progress(): Modifier =
    if (this is ProgressStatus.Completed) {
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
            status = ProgressStatus.Completed(80),
            onClick = {}
        )
    }
}

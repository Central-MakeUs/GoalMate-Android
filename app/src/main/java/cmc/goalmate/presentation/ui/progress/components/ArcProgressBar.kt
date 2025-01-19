package cmc.goalmate.presentation.ui.progress.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors

@Composable
fun ArcProgressBar(
    percent: Float,
    modifier: Modifier = Modifier,
) {
    val barColor = MaterialTheme.goalMateColors.secondary02
    val backgroundColor = MaterialTheme.goalMateColors.secondaryVariant

    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier.size(240.dp, 140.dp).padding(20.dp),
        ) {
            drawArc(
                color = backgroundColor,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                size = Size(200.dp.toPx(), 200.dp.toPx()),
                style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round),
            )
            drawArc(
                color = barColor,
                startAngle = 180f,
                sweepAngle = percent,
                useCenter = false,
                size = Size(200.dp.toPx(), 200.dp.toPx()),
                style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round),
            )
        }
    }
}

@Composable
@Preview
private fun ArcProgressBarPreview() {
    GoalMateTheme {
        ArcProgressBar(
            percent = 5f,
        )
    }
}

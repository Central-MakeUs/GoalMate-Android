package cmc.goalmate.presentation.ui.progress.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    val trackBackgroundColor = if (progress == 1f) MaterialTheme.colorScheme.primary else MaterialTheme.goalMateColors.completed
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.clip(RoundedCornerShape(GoalMateDimens.CompletedProgressBarHeight)),
        ) {
            Slider(
                value = if (progress == 1f) 0.99f else progress,
                onValueChange = {},
                colors = SliderDefaults.colors(thumbColor = Color.Transparent),
                thumb = {
                    Icon(
                        painter = painterResource(R.drawable.icon_flag),
                        contentDescription = null,
                        tint = MaterialTheme.goalMateColors.primary,
                        modifier = Modifier.padding(bottom = 29.dp).padding(start = 4.dp),
                    )
                },
                track = { sliderState ->
                    SliderDefaults.Track(
                        colors = SliderDefaults.colors(
                            activeTrackColor = MaterialTheme.colorScheme.primary,
                            inactiveTrackColor = MaterialTheme.goalMateColors.completed,
                        ),
                        enabled = true,
                        sliderState = sliderState,
                        drawStopIndicator = null,
                        trackInsideCornerSize = if (progress == 1f) 14.dp else 0.dp,
                        thumbTrackGapSize = 0.dp,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(GoalMateDimens.CompletedProgressBarHeight),
                            ).background(trackBackgroundColor),
                    )
                },
            )
        }
        Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.goalMateTypography.bodySmallMedium,
            color = MaterialTheme.goalMateColors.textButton,
            modifier = Modifier.offset(x = (-6).dp,y = 8.dp).align(alignment = Alignment.BottomEnd),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun CompletedProgressBarPreview() {
    GoalMateTheme {
        CompletedProgressBar(progress = 0.5f, modifier = Modifier.fillMaxWidth())
    }
}

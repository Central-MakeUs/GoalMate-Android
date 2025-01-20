package cmc.goalmate.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.components.TagSize.LARGE
import cmc.goalmate.presentation.components.TagSize.SMALL
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun TextTag(
    text: String,
    textColor: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.goalMateTypography.caption,
) {
    Text(
        text = text,
        style = textStyle,
        color = textColor,
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(4.dp),
            )
            .padding(vertical = 2.dp, horizontal = 4.dp),
    )
}

enum class TagSize(val height: Dp, val labelText: String) {
    SMALL(26.dp, "남음"),
    LARGE(31.dp, "잔여"),
}

@Composable
fun ParticipationStatusTag(
    remainingCount: Int,
    participantsCount: Int,
    tagSize: TagSize,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.height(tagSize.height),
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.goalMateColors.secondary01,
                    shape = RoundedCornerShape(topStart = 7.dp, bottomStart = 7.dp),
                )
                .padding(start = 8.dp, end = 12.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            CountWithLabel(
                count = remainingCount,
                label = tagSize.labelText,
                labelColor = MaterialTheme.goalMateColors.onSecondary,
                tagSize = tagSize,
            )
        }

        TriangleCanvas(
            backgroundColor = MaterialTheme.goalMateColors.secondary01Variant,
            modifier = Modifier
                .size(width = 8.dp, height = tagSize.height)
                .offset(x = (-8).dp),
        )

        Box(
            modifier = Modifier
                .offset(x = (-8).dp)
                .background(
                    color = MaterialTheme.goalMateColors.secondary01Variant,
                    shape = RoundedCornerShape(topEnd = 7.dp, bottomEnd = 7.dp),
                )
                .padding(start = 2.dp, end = 8.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            CountWithLabel(
                count = participantsCount,
                label = "참여중",
                labelColor = MaterialTheme.goalMateColors.secondary01,
                tagSize = tagSize,
            )
        }
    }
}

@Composable
private fun TagSize.countTextStyle(): TextStyle =
    when (this) {
        SMALL -> MaterialTheme.goalMateTypography.tagSmall
        LARGE -> MaterialTheme.goalMateTypography.tagLarge
    }

@Composable
private fun TagSize.labelTextStyle(): TextStyle =
    when (this) {
        SMALL -> MaterialTheme.goalMateTypography.labelSmall
        LARGE -> MaterialTheme.goalMateTypography.captionMedium
    }

@Composable
private fun CountWithLabel(
    count: Int,
    label: String,
    labelColor: Color,
    tagSize: TagSize,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text = "$count",
            style = tagSize.countTextStyle(),
            color = labelColor,
        )
        Text(
            text = label,
            style = tagSize.labelTextStyle(),
            color = labelColor,
        )
    }
}

@Composable
private fun TriangleCanvas(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier,
    ) {
        val trianglePath = Path().apply {
            moveTo(x = 0f, y = size.height)
            lineTo(x = size.width, y = 0f)
            lineTo(x = size.width + 1f, y = 0f)
            lineTo(x = size.width + 1f, y = size.height + 1f)
            close()
        }
        drawPath(
            color = backgroundColor,
            path = trianglePath,
        )
    }
}

@Composable
@Preview
private fun ParticipationStatusTagLargePreview() {
    GoalMateTheme {
        ParticipationStatusTag(
            remainingCount = 0,
            participantsCount = 0,
            tagSize = LARGE,
        )
    }
}

@Composable
@Preview
private fun ParticipationStatusTagSmallPreview() {
    GoalMateTheme {
        ParticipationStatusTag(
            remainingCount = 0,
            participantsCount = 0,
            tagSize = SMALL,
        )
    }
}

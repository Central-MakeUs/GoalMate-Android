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
import cmc.goalmate.presentation.theme.color.Secondary01_400
import cmc.goalmate.presentation.theme.color.Secondary02_700
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.home.GoalUiStatus

enum class TextTagSize(val horizontal: Dp, val vertical: Dp) {
    SMALL(horizontal = 4.dp, vertical = 2.dp),
    MEDIUM(horizontal = 6.dp, vertical = 4.dp),
}

@Composable
fun TextTag(
    text: String,
    textColor: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.goalMateTypography.caption,
    size: TextTagSize = TextTagSize.SMALL,
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
            .padding(vertical = size.vertical, horizontal = size.horizontal),
    )
}

@Composable
@Preview
private fun TextTagPreview() {
    GoalMateTheme {
        TextTag(
            text = "마감임박",
            textColor = MaterialTheme.goalMateColors.background,
            backgroundColor = Secondary02_700,
            textStyle = MaterialTheme.goalMateTypography.captionMedium,
            size = TextTagSize.MEDIUM,
        )
    }
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
    goalUiStatus: GoalUiStatus,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.height(tagSize.height),
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = goalUiStatus.startBackgroundColor(),
                    shape = RoundedCornerShape(topStart = 7.dp, bottomStart = 7.dp),
                )
                .padding(start = 8.dp, end = 12.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            CountWithLabel(
                count = remainingCount,
                label = tagSize.labelText,
                labelColor = goalUiStatus.onStartBackgroundColor(),
                tagSize = tagSize,
            )
        }

        TriangleCanvas(
            backgroundColor = goalUiStatus.endBackgroundColor(),
            modifier = Modifier
                .size(width = 8.dp, height = tagSize.height)
                .offset(x = (-8).dp),
        )

        Box(
            modifier = Modifier
                .offset(x = (-8).dp)
                .background(
                    color = goalUiStatus.endBackgroundColor(),
                    shape = RoundedCornerShape(topEnd = 7.dp, bottomEnd = 7.dp),
                )
                .padding(start = 2.dp, end = 8.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            CountWithLabel(
                count = participantsCount,
                label = "참여중",
                labelColor = goalUiStatus.onEndBackgroundColor(),
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
fun GoalUiStatus.startBackgroundColor(): Color =
    when (this) {
        GoalUiStatus.AVAILABLE -> Secondary01_400
        GoalUiStatus.SOLD_OUT -> MaterialTheme.goalMateColors.finished
    }

@Composable
fun GoalUiStatus.onStartBackgroundColor(): Color =
    when (this) {
        GoalUiStatus.AVAILABLE -> MaterialTheme.goalMateColors.onSecondary
        GoalUiStatus.SOLD_OUT -> MaterialTheme.goalMateColors.background
    }

@Composable
fun GoalUiStatus.endBackgroundColor(): Color =
    when (this) {
        GoalUiStatus.AVAILABLE -> MaterialTheme.goalMateColors.secondary01Variant
        GoalUiStatus.SOLD_OUT -> MaterialTheme.goalMateColors.pending
    }

@Composable
fun GoalUiStatus.onEndBackgroundColor(): Color =
    when (this) {
        GoalUiStatus.AVAILABLE -> MaterialTheme.goalMateColors.secondary01
        GoalUiStatus.SOLD_OUT -> MaterialTheme.goalMateColors.onSurfaceVariant
    }

@Composable
@Preview
private fun ParticipationStatusTagLargePreview() {
    GoalMateTheme {
        ParticipationStatusTag(
            remainingCount = 0,
            participantsCount = 0,
            tagSize = LARGE,
            goalUiStatus = GoalUiStatus.AVAILABLE,
        )
    }
}

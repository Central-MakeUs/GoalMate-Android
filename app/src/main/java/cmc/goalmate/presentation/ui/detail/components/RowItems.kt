package cmc.goalmate.presentation.ui.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun SubTitleText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.goalMateTypography.subtitleSmall,
        color = MaterialTheme.goalMateColors.labelTitle,
        modifier = modifier,
    )
}

@Composable
fun InfoRow(
    title: String,
    content: String,
    modifier: Modifier = Modifier,
    annotatedContent: AnnotatedString? = null,
    extraContent: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
    ) {
        SubTitleText(
            text = title,
            modifier = Modifier.defaultMinSize(minWidth = 60.dp),
        )
        Spacer(Modifier.width(30.dp))
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = annotatedContent ?: AnnotatedString(content),
                style = MaterialTheme.goalMateTypography.buttonLabelLarge,
                color = MaterialTheme.goalMateColors.onBackground,
            )
            if (extraContent != null) {
                Spacer(Modifier.size(8.dp))
                extraContent.invoke()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun InfoRowPreview() {
    GoalMateTheme {
        InfoRow(
            title = "목표",
            content = "목표명",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun RowItem(
    labelText: String,
    contentText: String,
    labelStyle: TextStyle,
    contentStyle: TextStyle,
    labelBackgroundColor: Color,
    labelTextColor: Color,
    contentTextColor: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = labelText,
            style = labelStyle,
            color = labelTextColor,
            modifier = Modifier
                .background(
                    color = labelBackgroundColor,
                    shape = RoundedCornerShape(6.dp),
                )
                .padding(6.dp),
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = contentText,
            style = contentStyle,
            color = contentTextColor,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
fun WeeklyRowItem(
    label: String,
    content: String,
    modifier: Modifier = Modifier,
) {
    RowItem(
        labelText = label,
        contentText = content,
        labelStyle = MaterialTheme.goalMateTypography.captionSemiBold,
        contentStyle = MaterialTheme.goalMateTypography.subtitleSmall,
        labelBackgroundColor = MaterialTheme.goalMateColors.primaryVariant,
        labelTextColor = MaterialTheme.goalMateColors.onPrimaryVariant,
        contentTextColor = MaterialTheme.goalMateColors.onSurface,
        modifier = modifier,
    )
}

@Composable
fun MilestoneRowItem(
    label: String,
    content: String,
    modifier: Modifier = Modifier,
) {
    RowItem(
        labelText = label,
        contentText = content,
        labelStyle = MaterialTheme.goalMateTypography.captionRegular,
        contentStyle = MaterialTheme.goalMateTypography.subtitleSmall,
        labelBackgroundColor = MaterialTheme.goalMateColors.secondary02Variant,
        labelTextColor = MaterialTheme.goalMateColors.onSecondary02Variant,
        contentTextColor = MaterialTheme.goalMateColors.onBackground,
        modifier = modifier,
    )
}

@Composable
@Preview(showBackground = true)
private fun WeeklyRowItemPreview() {
    GoalMateTheme {
        WeeklyRowItem(
            label = "1주",
            content = "간단한 코딩부터 시작하기",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun MilestoneRowItemPreview() {
    GoalMateTheme {
        MilestoneRowItem(
            label = "1",
            content = "간단한 코딩부터 시작하기",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

package cmc.goalmate.presentation.ui.comments.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun DailyComment(
    comment: CommentUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(GoalMateDimens.VerticalArrangementSpaceMedium),
        modifier = modifier,
    ) {
        Text(
            text = comment.date,
            style = MaterialTheme.goalMateTypography.bodySmallMedium,
            color = MaterialTheme.goalMateColors.textButton,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        comment.messages.forEach { message ->
            DailyCommentItem(
                content = message.content,
                sender = message.sender,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun DailyCommentItem(
    content: String,
    sender: Sender,
    modifier: Modifier,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        if (sender == Sender.MENTEE) Spacer(Modifier.width(80.dp))

        Text(
            text = content,
            style = MaterialTheme.goalMateTypography.bodySmallMedium,
            color = MaterialTheme.goalMateColors.textButton,
            modifier = Modifier
                .background(
                    color = sender.backgroundColor(),
                    shape = RoundedCornerShape(24.dp),
                )
                .padding(GoalMateDimens.HorizontalPadding),
        )

        if (sender == Sender.MENTOR) Spacer(Modifier.width(80.dp))
    }
}

@Composable
fun Sender.backgroundColor(): Color =
    when (this) {
        Sender.MENTEE -> MaterialTheme.goalMateColors.completed
        Sender.MENTOR -> MaterialTheme.goalMateColors.surfaceVariant
    }

@Composable
@Preview
private fun DailyCommentPreview() {
    GoalMateTheme {
        DailyComment(comment = CommentUiModel.DUMMY)
    }
}

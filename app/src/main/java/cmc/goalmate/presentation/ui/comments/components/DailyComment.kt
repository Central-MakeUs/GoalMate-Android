package cmc.goalmate.presentation.ui.comments.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.components.TextTag
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.Primary600
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.comments.detail.CommentAction
import cmc.goalmate.presentation.ui.comments.detail.model.CommentUiModel
import cmc.goalmate.presentation.ui.comments.detail.model.SenderUiModel

@Composable
fun DailyComment(
    comment: CommentUiModel,
    onAction: (CommentAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(GoalMateDimens.VerticalArrangementSpaceMedium),
        modifier = modifier,
    ) {
        CommentDateHeader(
            commentDate = comment.displayedDate,
            dDayText = "${comment.daysFromStart}",
            modifier = Modifier.fillMaxWidth(),
        )
        comment.messages.forEach { message ->
            DailyCommentItem(
                messageId = message.id,
                content = message.content,
                sender = message.sender,
                onAction = onAction,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun CommentDateHeader(
    commentDate: String,
    dDayText: String,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Text(
            text = commentDate,
            style = MaterialTheme.goalMateTypography.bodySmallMedium,
            color = MaterialTheme.goalMateColors.textButton,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.size(6.dp))
        TextTag(
            text = "D-$dDayText",
            textColor = MaterialTheme.goalMateColors.onBackground,
            backgroundColor = MaterialTheme.goalMateColors.secondary02,
            textStyle = MaterialTheme.goalMateTypography.captionSemiBold,
        )
    }
}

@Composable
private fun DailyCommentItem(
    messageId: Int,
    content: String,
    sender: SenderUiModel,
    onAction: (CommentAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        if (sender == SenderUiModel.MENTEE) Spacer(Modifier.width(80.dp))

        Column {
            Text(
                text = content,
                style = MaterialTheme.goalMateTypography.body,
                color = sender.textColor(),
                modifier = Modifier
                    .background(
                        color = sender.backgroundColor(),
                        shape = RoundedCornerShape(24.dp),
                    )
                    .padding(GoalMateDimens.HorizontalPadding)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                if (sender == SenderUiModel.MENTEE) {
                                    isDropDownMenuExpanded = !isDropDownMenuExpanded
                                }
                            },
                        )
                    },
            )
            GoalMateDropDownMenu(
                isDropDownMenuExpanded = isDropDownMenuExpanded,
                onDismissRequest = { isDropDownMenuExpanded = false },
                onEditClicked = { onAction(CommentAction.EditComment(commentId = messageId)) },
                onDeleteClicked = { onAction(CommentAction.DeleteComment(commentId = messageId)) },
            )
        }

        if (sender == SenderUiModel.MENTOR) Spacer(Modifier.width(80.dp))
    }
}

@Composable
private fun SenderUiModel.textColor(): Color =
    when (this) {
        SenderUiModel.MENTEE -> MaterialTheme.goalMateColors.onBackground
        SenderUiModel.MENTOR -> MaterialTheme.goalMateColors.textButton
        SenderUiModel.ADMIN -> MaterialTheme.goalMateColors.textButton
    }

@Composable
private fun SenderUiModel.backgroundColor(): Color =
    when (this) {
        SenderUiModel.MENTEE -> Primary600
        SenderUiModel.MENTOR -> MaterialTheme.goalMateColors.surfaceVariant
        SenderUiModel.ADMIN -> MaterialTheme.goalMateColors.surfaceVariant
    }

@Composable
@Preview
private fun DailyCommentPreview() {
    GoalMateTheme {
        DailyComment(comment = CommentUiModel.DUMMY, onAction = {})
    }
}

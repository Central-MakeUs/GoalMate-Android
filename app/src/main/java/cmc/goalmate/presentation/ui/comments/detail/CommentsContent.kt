package cmc.goalmate.presentation.ui.comments.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.comments.components.DailyComment
import cmc.goalmate.presentation.ui.comments.components.MessageTextField
import cmc.goalmate.presentation.ui.comments.detail.model.CommentUiModel

@Composable
fun CommentsContent(
    comments: List<CommentUiModel>,
    modifier: Modifier = Modifier,
) {
    var inputs by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(
                horizontal = GoalMateDimens.HorizontalPadding,
                vertical = GoalMateDimens.BottomMargin,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (comments.isEmpty()) {
            Box(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.comments_detail_empty),
                    style = MaterialTheme.goalMateTypography.body,
                    color = MaterialTheme.goalMateColors.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
            return
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(44.dp),
                modifier = Modifier.weight(1f),
            ) {
                items(items = comments) { comment ->
                    DailyComment(
                        comment = comment,
                        modifier = Modifier,
                    )
                }
            }
        }
        CommentTextField(
            commentText = inputs,
            onCommentTextChanged = { inputs = it },
            onSubmitButtonClicked = {},
        )
    }
}

@Composable
private fun CommentTextField(
    commentText: String,
    onCommentTextChanged: (String) -> Unit,
    onSubmitButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(GoalMateDimens.VerticalArrangementSpaceMedium),
        modifier = modifier,
    ) {
        MessageTextField(
            value = commentText,
            onValueChange = onCommentTextChanged,
            modifier = Modifier.weight(1f),
        )
        SubmitButton(
            onClick = onSubmitButtonClicked,
            enabled = commentText.isNotBlank(),
        )
    }
}

@Composable
private fun SubmitButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (enabled) {
        MaterialTheme.goalMateColors.primary
    } else {
        MaterialTheme.goalMateColors.surface
    }

    val iconTint = if (enabled) {
        MaterialTheme.goalMateColors.onPrimary
    } else {
        MaterialTheme.goalMateColors.background
    }

    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = CircleShape,
            )
            .size(44.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.icon_upload),
            contentDescription = null,
            tint = iconTint,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CommentsContentPreview() {
    GoalMateTheme {
        CommentsContent(
            comments = listOf(CommentUiModel.DUMMY, CommentUiModel.DUMMY),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
        )
    }
}

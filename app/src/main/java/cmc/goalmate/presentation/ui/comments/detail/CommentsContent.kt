package cmc.goalmate.presentation.ui.comments.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.comments.components.DailyComment
import cmc.goalmate.presentation.ui.comments.detail.model.CommentUiModel

@Composable
fun CommentsContent(
    comments: List<CommentUiModel>,
    onAction: (CommentAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (comments.isEmpty()) {
        Box(modifier = modifier) {
            Text(
                text = stringResource(R.string.comments_detail_empty),
                style = MaterialTheme.goalMateTypography.body,
                color = MaterialTheme.goalMateColors.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(44.dp),
            modifier = modifier,
        ) {
            items(items = comments, key = { it.date }) { comment ->
                DailyComment(
                    comment = comment,
                    onAction = onAction,
                    modifier = Modifier,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CommentsContentPreview() {
    GoalMateTheme {
        CommentsContent(
            comments = listOf(CommentUiModel.DUMMY, CommentUiModel.DUMMY),
            onAction = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
        )
    }
}

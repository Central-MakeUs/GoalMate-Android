package cmc.goalmate.presentation.ui.comments.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.ui.comments.detail.components.CommentDateHeader
import cmc.goalmate.presentation.ui.comments.detail.components.DailyCommentItem
import cmc.goalmate.presentation.ui.comments.detail.model.CommentUiModel
import java.time.LocalDate

@Composable
fun CommentsList(
    comments: List<CommentUiModel>,
    isNewMessageAdded: Boolean,
    onAction: (CommentAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    var isFirstLoad by remember { mutableStateOf(true) }

    LaunchedEffect(comments.size) {
        if (comments.isEmpty()) return@LaunchedEffect
        if (isFirstLoad) {
            listState.scrollToItem(comments.size - 1)
            isFirstLoad = false
            return@LaunchedEffect
        }
        if (!isNewMessageAdded) return@LaunchedEffect
        listState.animateScrollToItem(comments.size - 1)
    }

    OnScrollToTop(listState) {
        onAction(CommentAction.LoadMoreComment)
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(44.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
        state = listState,
        reverseLayout = true,
    ) {
        items(items = comments, key = { it.date }) { comment ->
            comment.messages.forEachIndexed { index, message ->
                DailyCommentItem(
                    messageId = message.id,
                    content = message.content,
                    sender = message.sender,
                    onAction = onAction,
                    modifier = Modifier.fillMaxWidth(),
                )
                if (index < comment.messages.size - 1) {
                    Spacer(modifier = Modifier.height(GoalMateDimens.VerticalArrangementSpaceMedium))
                }
            }

            Spacer(Modifier.size(GoalMateDimens.VerticalArrangementSpaceMedium))

            CommentDateHeader(
                commentDate = comment.displayedDate,
                dDayText = "${comment.daysFromStart}",
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun LazyListState.shouldLoadMore(): Boolean {
    return remember(this) {
        derivedStateOf {
            if (layoutInfo.totalItemsCount == 0) {
                false
            } else {
                val firstVisibleItem = layoutInfo.visibleItemsInfo.firstOrNull() ?: return@derivedStateOf false
                firstVisibleItem.index <= 1
            }
        }
    }.value
}

@Composable
private fun OnScrollToTop(
    scrollState: LazyListState,
    callback: () -> Unit,
) {
    val shouldLoadMore = scrollState.shouldLoadMore()
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) callback.invoke()
    }
}

@Composable
@Preview(showBackground = true)
fun CommentsContentPreview() {
    val commentsDummy = (1..20).map { CommentUiModel.DUMMY.copy(date = LocalDate.of(2024, 11, it)) }

    GoalMateTheme {
        CommentsList(
            comments = commentsDummy,
            isNewMessageAdded = false,
            onAction = {
                Log.d("yenny", "event: $it")
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(GoalMateDimens.HorizontalPadding)
                    .background(Color.White),
        )
    }
}

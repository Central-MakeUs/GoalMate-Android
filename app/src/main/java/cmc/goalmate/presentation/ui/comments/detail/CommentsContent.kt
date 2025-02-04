package cmc.goalmate.presentation.ui.comments.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.comments.detail.MessageUiModel.Companion.DUMMY_MENTEE
import cmc.goalmate.presentation.ui.comments.detail.MessageUiModel.Companion.DUMMY_MENTOR

enum class Sender {
    MENTOR,
    MENTEE,
}

data class MessageUiModel(val content: String, val sender: Sender) {
    companion object {
        val DUMMY_MENTEE = MessageUiModel(
            "선생님 영어 단어 암기 꿀팁좀 주세요ㅠㅠ선생님 영어 단어 암기 꿀팁좀 주세요ㅠㅠ선생님 영어 단어 암기 꿀팁좀 주세요ㅠㅠ",
            Sender.MENTEE,
        )
        val DUMMY_MENTOR = MessageUiModel("영어 단어 암기는 이렇게 하시면 됩니당", Sender.MENTOR)
    }
}

data class CommentUiModel(val date: String, val messages: List<MessageUiModel>) {
    companion object {
        val DUMMY =
            CommentUiModel(date = "2024년 11월 25일", messages = listOf(DUMMY_MENTEE, DUMMY_MENTOR))
    }
}

@Composable
fun CommentsContent(
    comments: List<CommentUiModel>,
    modifier: Modifier = Modifier,
) {
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
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(44.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(items = comments) { comment ->
                DailyComment(
                    comment = comment,
                    modifier = Modifier,
                )
            }
        }
    }
}

@Composable
private fun CommentTextField(modifier: Modifier = Modifier) {
    Row {
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CommentsContentPreview() {
    GoalMateTheme {
        CommentTextField()
        CommentsContent(
            comments = listOf(CommentUiModel.DUMMY, CommentUiModel.DUMMY),
            modifier = Modifier.fillMaxWidth().background(Color.White),
        )
    }
}

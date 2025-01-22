package cmc.goalmate.presentation.ui.progress.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.TextTag
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.Primary700
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

data class CommentUiModel(val content: String, val date: String, val mentor: String) {
    companion object {
        val DUMMY = CommentUiModel(
            content = "이제 시작한지 얼마 안 됐는데, 척척 해내는" +
                "김골메이트님 너무 기특합니다!",
            date = "2025년 01월 02일",
            mentor = "다온",
        )
    }
}

@Composable
private fun CommentStyleContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.goalMateColors.primaryVariant,
                shape = RoundedCornerShape(24.dp),
            )
            .border(
                width = 3.dp,
                color = MaterialTheme.goalMateColors.thinDivider,
                shape = RoundedCornerShape(24.dp),
            )
            .padding(GoalMateDimens.HorizontalPadding),
    ) {
        content()
    }
}

@Composable
fun RecentComment(
    comment: CommentUiModel,
    modifier: Modifier = Modifier,
) {
    CommentStyleContainer(modifier = modifier) {
        Column {
            Text(
                text = comment.content,
                style = MaterialTheme.goalMateTypography.body,
                color = MaterialTheme.goalMateColors.onSurface,
            )
            Spacer(Modifier.size(16.dp))
            Text(
                text = comment.date,
                textAlign = TextAlign.End,
                modifier = Modifier.align(Alignment.End).padding(bottom = 4.dp),
                style = MaterialTheme.goalMateTypography.caption,
                color = MaterialTheme.goalMateColors.onSurface,
            )
            Text(
                text = stringResource(R.string.goal_comment_mentor, comment.mentor),
                textAlign = TextAlign.End,
                modifier = Modifier.align(Alignment.End),
                style = MaterialTheme.goalMateTypography.caption,
                color = MaterialTheme.goalMateColors.onSurface,
            )
        }
    }
}

@Composable
fun CommentItem(
    comment: CommentUiModel,
    modifier: Modifier = Modifier,
) {
    CommentStyleContainer(modifier = modifier) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                TextTag(
                    text = "D+20",
                    textColor = MaterialTheme.goalMateColors.primaryVariant,
                    textStyle = MaterialTheme.goalMateTypography.captionSemiBold,
                    backgroundColor = Primary700,
                )
                Text(
                    text = comment.date,
                    style = MaterialTheme.goalMateTypography.subtitleSmall,
                    color = MaterialTheme.goalMateColors.onPrimaryVariant,
                )
            }
            Spacer(Modifier.size(8.dp))
            Text(
                text = comment.content,
                style = MaterialTheme.goalMateTypography.bodySmallMedium,
                color = MaterialTheme.goalMateColors.textButton,
            )
            Spacer(Modifier.size(14.dp))
            Text(
                text = stringResource(R.string.goal_comment_mentor, comment.mentor),
                textAlign = TextAlign.End,
                modifier = Modifier.align(Alignment.End),
                style = MaterialTheme.goalMateTypography.caption,
                color = MaterialTheme.goalMateColors.onPrimaryVariant,
            )
        }
    }
}

@Composable
fun FinalMessage(
    mentee: String,
    mentor: String,
    message: String,
    modifier: Modifier = Modifier,
) {
    CommentStyleContainer(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(R.string.goal_comment_mentee, mentee),
                style = MaterialTheme.goalMateTypography.body,
                color = MaterialTheme.goalMateColors.onPrimaryVariant,
            )
            Text(
                text = message,
                style = MaterialTheme.goalMateTypography.body,
                color = MaterialTheme.goalMateColors.onBackground,
            )
            Text(
                text = stringResource(R.string.goal_comment_mentor, mentor),
                style = MaterialTheme.goalMateTypography.body,
                color = MaterialTheme.goalMateColors.onPrimaryVariant,
                modifier = Modifier.align(Alignment.End),
            )
        }
    }
}

@Composable
@Preview
private fun RecentCommentPreview() {
    GoalMateTheme {
        RecentComment(
            comment = CommentUiModel.DUMMY,
        )
    }
}

@Composable
@Preview
private fun CommentItemPreview() {
    GoalMateTheme {
        CommentItem(
            comment = CommentUiModel.DUMMY,
        )
    }
}

@Composable
@Preview
private fun FinalMessagePreview() {
    GoalMateTheme {
        FinalMessage(
            mentee = "예니",
            mentor = "다온",
            message = "김골메이트님!\n" +
                "30일동안 고생 많았어요~! 어떠셨어요? 조금 힘들었죠? 하지만 지금 김골메이트님은 30일 전보다 훨씬 성장했답니다! 앞으로도 응원할게요!김골메이트님!\n" +
                "30일동안 고생 많았어요~! 어떠셨어요? 조금 힘들었죠? 하지만 지금 김골메이트님은 30일 전보다 훨씬 성장했답니다! 앞으로도 응원할게요!김골메이트님!",
        )
    }
}

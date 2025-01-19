package cmc.goalmate.presentation.ui.progress.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
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
fun CommentItem(
    comment: CommentUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
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
            text = "from. ${comment.mentor}",
            textAlign = TextAlign.End,
            modifier = Modifier.align(Alignment.End),
            style = MaterialTheme.goalMateTypography.caption,
            color = MaterialTheme.goalMateColors.onSurface,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun GoalStartScreenPreview() {
    GoalMateTheme {
        CommentItem(
            comment = CommentUiModel.DUMMY,
        )
    }
}

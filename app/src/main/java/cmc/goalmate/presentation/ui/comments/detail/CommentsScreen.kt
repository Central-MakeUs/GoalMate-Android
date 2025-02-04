package cmc.goalmate.presentation.ui.comments.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.presentation.components.AppBarWithBackButton
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.progress.components.CommentItem
import cmc.goalmate.presentation.ui.progress.components.CommentUiModel

@Composable
fun CommentsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CommentsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = modifier) {
        AppBarWithBackButton(
            title = "멘토 코멘트",
            onBackButtonClicked = navigateBack,
        )
        CommentsContent(
            comments = state.comments,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun CommentsContent(
    comments: List<CommentUiModel>,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(
                horizontal = GoalMateDimens.HorizontalPadding,
                vertical = GoalMateDimens.BottomMargin,
            ),
    ) {
        if (comments.isEmpty()) {
            Text(
                text = "아직 멘토의 코멘트가 없어요 :)",
                style = MaterialTheme.goalMateTypography.body,
                color = MaterialTheme.goalMateColors.onBackground,
                modifier = Modifier.align(Alignment.Center),
            )
            return
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(GoalMateDimens.VerticalArrangementSpaceMedium),
        ) {
            items(items = comments) { comment ->
                CommentItem(
                    comment = comment,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CommentsScreenPreview() {
    GoalMateTheme {
        CommentsContent(
            comments = listOf(CommentUiModel.DUMMY, CommentUiModel.DUMMY),
            modifier = Modifier.background(Color.White),
        )
    }
}

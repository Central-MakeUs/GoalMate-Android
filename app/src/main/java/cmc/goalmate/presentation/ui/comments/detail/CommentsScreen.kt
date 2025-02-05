package cmc.goalmate.presentation.ui.comments.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import cmc.goalmate.presentation.components.AppBarWithBackButton

@Composable
fun CommentsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CommentsViewModel = hiltViewModel(),
) {
//    val state by viewModel.state.collectAsStateWithLifecycle()

    Column {
        AppBarWithBackButton(
            onBackButtonClicked = navigateBack,
            title = "ANNA와 함께하는 영어 완전 ...",
        )

        CommentsContent(
            comments = listOf(CommentUiModel.DUMMY, CommentUiModel.DUMMY),
            modifier = Modifier,
        )
    }
}

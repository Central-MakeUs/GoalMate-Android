package cmc.goalmate.presentation.ui.comments.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.presentation.components.AppBarWithBackButton

@Composable
fun CommentsDetailScreen(
    goalTitle: String,
    navigateBack: () -> Unit,
    viewModel: CommentsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column {
        AppBarWithBackButton(
            onBackButtonClicked = navigateBack,
            title = goalTitle,
        )
        CommentsDetailContent(state = state)
    }
}

@Composable
private fun CommentsDetailContent(
    state: CommentsUiState,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is CommentsUiState.Success -> {
            CommentsContent(
                comments = state.comments,
                modifier = modifier,
            )
        }
        CommentsUiState.Error -> {}
        CommentsUiState.Loading -> {}
    }
}

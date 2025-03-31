package cmc.goalmate.presentation.ui.comments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.presentation.components.EmptyGoalContents
import cmc.goalmate.presentation.components.ErrorScreen
import cmc.goalmate.presentation.components.HeaderTitle
import cmc.goalmate.presentation.ui.main.navigation.CommentDetailParams
import cmc.goalmate.presentation.ui.main.navigation.NavigateToCommentDetail
import cmc.goalmate.presentation.ui.util.ObserveAsEvent

@Composable
fun CommentRoomsScreen(
    navigateToCommentDetail: NavigateToCommentDetail,
    navigateToHome: () -> Unit,
    viewModel: GoalCommentsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) { event ->
        when (event) {
            is CommentRoomsEvent.NavigateToCommentDetail ->
                navigateToCommentDetail(
                    CommentDetailParams(roomId = event.roomId, goalTitle = event.goalTitle, endDate = event.endDate),
                )
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HeaderTitle(
            title = "코멘트",
            modifier = Modifier.fillMaxWidth(),
        )

        when (state) {
            is GoalCommentsUiState.LoggedIn -> {
                if (state.hasNoGoals()) {
                    EmptyGoalContents(
                        onButtonClicked = navigateToHome,
                        modifier = Modifier.fillMaxSize(),
                    )
                    return
                }
                CommentRoomsContent(
                    goalComments = (state as GoalCommentsUiState.LoggedIn).commentRooms,
                    onAction = viewModel::onAction,
                )
            }
            GoalCommentsUiState.LoggedOut -> {
                EmptyGoalContents(
                    onButtonClicked = navigateToHome,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            GoalCommentsUiState.Loading -> {}
            GoalCommentsUiState.Error -> {
                ErrorScreen(
                    onRetryButtonClicked = {
                        viewModel.onAction(CommentRoomsAction.Retry)
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

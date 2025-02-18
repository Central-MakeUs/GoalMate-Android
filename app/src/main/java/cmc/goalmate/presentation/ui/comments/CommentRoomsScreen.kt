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
import cmc.goalmate.app.navigation.NavigateToGoal
import cmc.goalmate.presentation.components.EmptyGoalContents
import cmc.goalmate.presentation.components.HeaderTitle

@Composable
fun CommentRoomsScreen(
    navigateToCommentDetail: NavigateToGoal,
    navigateToHome: () -> Unit,
    viewModel: GoalCommentsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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
                GoalCommentsContent(
                    goalComments = (state as GoalCommentsUiState.LoggedIn).commentRooms,
                    navigateToCommentDetail = navigateToCommentDetail,
                )
            }
            GoalCommentsUiState.LoggedOut -> {
                EmptyGoalContents(
                    onButtonClicked = navigateToHome,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            GoalCommentsUiState.Error -> {}
            GoalCommentsUiState.Loading -> {}
        }
    }
}

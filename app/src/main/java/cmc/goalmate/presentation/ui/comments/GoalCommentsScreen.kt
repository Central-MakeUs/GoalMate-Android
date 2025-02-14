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
import cmc.goalmate.presentation.ui.common.UserState

@Composable
fun GoalCommentsScreen(
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

        when (val userGoalState = state.userGoalsState) {
            is UserState.LoggedIn -> {
                if (userGoalState.data.isEmpty()) {
                    EmptyGoalContents(
                        onButtonClicked = navigateToHome,
                        modifier = Modifier.fillMaxSize(),
                    )
                    return
                }
                GoalCommentsContent(
                    goalComments = userGoalState.data,
                    navigateToCommentDetail = navigateToCommentDetail,
                )
            }
            UserState.LoggedOut -> {
                EmptyGoalContents(
                    onButtonClicked = navigateToHome,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

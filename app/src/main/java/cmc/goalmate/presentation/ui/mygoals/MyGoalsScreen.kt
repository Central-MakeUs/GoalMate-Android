package cmc.goalmate.presentation.ui.mygoals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.R
import cmc.goalmate.presentation.components.EmptyGoalContents
import cmc.goalmate.presentation.components.ErrorScreen
import cmc.goalmate.presentation.components.HeaderTitle
import cmc.goalmate.presentation.ui.main.navigation.CompletedGoalParams
import cmc.goalmate.presentation.ui.main.navigation.InProgressGoalParams
import cmc.goalmate.presentation.ui.main.navigation.NavigateToCompleted
import cmc.goalmate.presentation.ui.main.navigation.NavigateToGoal
import cmc.goalmate.presentation.ui.main.navigation.NavigateToInProgress
import cmc.goalmate.presentation.ui.util.ObserveAsEvent

@Composable
fun MyGoalsScreen(
    navigateToCompletedGoal: NavigateToCompleted,
    navigateToProgressGoal: NavigateToInProgress,
    navigateToGoalDetail: NavigateToGoal,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyGoalsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) { event ->
        when (event) {
            is MyGoalsEvent.NavigateToCompleted -> {
                navigateToCompletedGoal(
                    CompletedGoalParams(
                        menteeGoalId = event.menteeGoalId,
                        roomId = event.roomId,
                        goalId = event.goalId,
                    ),
                )
            }
            is MyGoalsEvent.NavigateToGoalDetail -> {
                navigateToGoalDetail(event.goalId)
            }
            is MyGoalsEvent.NavigateToInProgress -> {
                navigateToProgressGoal(
                    InProgressGoalParams(
                        menteeGoalId = event.menteeGoalId,
                        goalTitle = event.goalTitle,
                        roomId = event.roomId,
                        goalId = event.goalId,
                    ),
                )
            }
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeaderTitle(
            title = stringResource(R.string.my_goals_header_title),
            modifier = Modifier.fillMaxWidth(),
        )
        when (state) {
            is MyGoalsUiState.LoggedIn -> {
                val loggedInState = (state as MyGoalsUiState.LoggedIn)
                if (loggedInState.myGoals.isEmpty()) {
                    EmptyGoalContents(
                        onButtonClicked = navigateToHome,
                    )
                    return
                }
                MyGoalsContent(
                    myGoals = loggedInState.myGoals,
                    onAction = viewModel::onAction,
                )
            }

            MyGoalsUiState.LoggedOut -> {
                EmptyGoalContents(
                    onButtonClicked = navigateToHome,
                )
            }

            is MyGoalsUiState.Error -> {
                ErrorScreen(
                    onRetryButtonClicked = { viewModel.onAction(MyGoalsAction.Retry) },
                    modifier = Modifier.fillMaxSize(),
                )
            }
            MyGoalsUiState.Loading -> {}
        }
    }
}

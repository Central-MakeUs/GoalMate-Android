package cmc.goalmate.presentation.ui.mygoals

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.R
import cmc.goalmate.app.navigation.NavigateToGoal
import cmc.goalmate.presentation.components.EmptyGoalContents
import cmc.goalmate.presentation.components.HeaderTitle

@Composable
fun MyGoalsScreen(
    navigateToCompletedGoal: NavigateToGoal,
    navigateToProgressGoal: NavigateToGoal,
    navigateToGoalDetail: NavigateToGoal,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyGoalsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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
                HandleLoggedInState(
                    myGoals = (state as MyGoalsUiState.LoggedIn).myGoals,
                    navigateToCompletedGoal = navigateToCompletedGoal,
                    navigateToProgressGoal = navigateToProgressGoal,
                    navigateToGoalDetail = navigateToGoalDetail,
                    navigateToHome = navigateToHome,
                )
            }

            MyGoalsUiState.LoggedOut -> {
                EmptyGoalContents(
                    onButtonClicked = navigateToHome,
                )
            }

            is MyGoalsUiState.Error -> {
                Log.d("yenny", "myGoals error : ${(state as MyGoalsUiState.Error).error}")
            }
            MyGoalsUiState.Loading -> {}
        }
    }
}

@Composable
private fun HandleLoggedInState(
    myGoals: List<MyGoalUiModel>,
    navigateToCompletedGoal: NavigateToGoal,
    navigateToProgressGoal: NavigateToGoal,
    navigateToGoalDetail: NavigateToGoal,
    navigateToHome: () -> Unit,
) {
    if (myGoals.isEmpty()) {
        EmptyGoalContents(
            onButtonClicked = navigateToHome,
        )
        return
    }
    MyGoalsContent(
        myGoals = myGoals,
        navigateToCompletedGoal = navigateToCompletedGoal,
        navigateToProgressGoal = navigateToProgressGoal,
        navigateToGoalDetail = navigateToGoalDetail,
    )
}

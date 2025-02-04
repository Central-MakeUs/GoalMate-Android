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
import cmc.goalmate.app.navigation.NavigateToGoal
import cmc.goalmate.presentation.components.HeaderTitle
import cmc.goalmate.presentation.ui.common.EmptyGoalContents

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
        if (state.hasNoGoals()) {
            EmptyGoalContents(
                onButtonClicked = navigateToHome,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            MyGoalsContent(
                myGoals = state.myGoals,
                navigateToCompletedGoal = navigateToCompletedGoal,
                navigateToProgressGoal = navigateToProgressGoal,
                navigateToGoalDetail = navigateToGoalDetail,
            )
        }
    }
}

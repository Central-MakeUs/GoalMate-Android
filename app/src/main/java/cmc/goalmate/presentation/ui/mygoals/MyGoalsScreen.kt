package cmc.goalmate.presentation.ui.mygoals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.R
import cmc.goalmate.app.navigation.NavigateToGoal
import cmc.goalmate.presentation.components.ButtonSize
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.components.ThickDivider
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

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
        if (state.hasNoGoals()) {
            HeaderTitle(modifier = Modifier.fillMaxWidth())
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

@Composable
private fun HeaderTitle(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.my_goals_header_title),
            style = MaterialTheme.goalMateTypography.subtitleMedium,
            modifier = Modifier.padding(
                horizontal = GoalMateDimens.HorizontalPadding,
                vertical = 14.dp,
            ),
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.goalMateColors.pending,
        )
    }
}

@Composable
private fun EmptyGoalContents(
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.my_goals_empty_message),
            style = MaterialTheme.goalMateTypography.body,
        )
        Spacer(modifier = Modifier.size(24.dp))
        GoalMateButton(
            content = stringResource(R.string.my_goals_empty_button),
            onClick = onButtonClicked,
            buttonSize = ButtonSize.LARGE,
        )
    }
}

@Composable
private fun MyGoalsContent(
    myGoals: List<MyGoalUiModel>,
    navigateToCompletedGoal: NavigateToGoal,
    navigateToProgressGoal: NavigateToGoal,
    navigateToGoalDetail: NavigateToGoal,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            HeaderTitle(modifier = Modifier.fillMaxWidth())
        }
        items(myGoals) { myGoal ->
            Column {
                when (myGoal.goalState) {
                    MyGoalState.IN_PROGRESS -> InProgressGoalItem(
                        myGoal = myGoal,
                        navigateToProgressPage = navigateToProgressGoal,
                        modifier = Modifier,
                    )

                    MyGoalState.COMPLETED -> CompletedGoalItem(
                        myGoal = myGoal,
                        navigateToGoalDetail = navigateToGoalDetail,
                        navigateToCompletedGoalPage = navigateToCompletedGoal,
                        modifier = Modifier,
                    )
                }
                ThickDivider()
            }
        }
    }
}

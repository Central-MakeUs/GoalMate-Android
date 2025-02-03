package cmc.goalmate.presentation.ui.mygoals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.app.navigation.NavigateToGoal
import cmc.goalmate.presentation.components.ButtonSize
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.components.ThickDivider
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.White
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun EmptyGoalContents(
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
fun MyGoalsContent(
    myGoals: List<MyGoalUiModel>,
    navigateToCompletedGoal: NavigateToGoal,
    navigateToProgressGoal: NavigateToGoal,
    navigateToGoalDetail: NavigateToGoal,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
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

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun MyGoalContentPreview() {
    GoalMateTheme {
        MyGoalsContent(
            myGoals = listOf(MyGoalUiModel.DUMMY, MyGoalUiModel.DUMMY2),
            {},
            {},
            {},
            modifier = Modifier.background(White),
        )
    }
}

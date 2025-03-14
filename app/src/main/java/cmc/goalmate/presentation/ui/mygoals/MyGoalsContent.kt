package cmc.goalmate.presentation.ui.mygoals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cmc.goalmate.presentation.components.ThickDivider
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.White
import cmc.goalmate.presentation.ui.main.navigation.CompletedGoalParams
import cmc.goalmate.presentation.ui.main.navigation.InProgressGoalParams
import cmc.goalmate.presentation.ui.main.navigation.NavigateToCompleted
import cmc.goalmate.presentation.ui.main.navigation.NavigateToGoal
import cmc.goalmate.presentation.ui.main.navigation.NavigateToInProgress

@Composable
fun MyGoalsContent(
    myGoals: List<MyGoalUiModel>,
    navigateToCompletedGoal: NavigateToCompleted,
    navigateToProgressGoal: NavigateToInProgress,
    navigateToGoalDetail: NavigateToGoal,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(myGoals) { myGoal ->
            Column {
                when (myGoal.goalState) {
                    MyGoalUiState.IN_PROGRESS -> InProgressGoalItem(
                        myGoal = myGoal,
                        onStartButtonClicked = {
                            navigateToProgressGoal(
                                InProgressGoalParams(
                                    menteeGoalId = myGoal.menteeGoalId,
                                    goalTitle = myGoal.title,
                                    roomId = myGoal.roomId,
                                    goalId = myGoal.goalId,
                                ),
                            )
                        },
                        modifier = Modifier,
                    )

                    MyGoalUiState.COMPLETED -> CompletedGoalItem(
                        myGoal = myGoal,
                        onCompletedButtonClicked = {
                            navigateToCompletedGoal(
                                CompletedGoalParams(
                                    menteeGoalId = myGoal.menteeGoalId,
                                    roomId = myGoal.roomId,
                                    goalId = myGoal.goalId,
                                ),
                            )
                        },
                        onRestartButtonClicked = { navigateToGoalDetail(myGoal.goalId) },
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

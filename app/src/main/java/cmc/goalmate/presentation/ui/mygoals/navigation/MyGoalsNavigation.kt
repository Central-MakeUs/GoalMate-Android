package cmc.goalmate.presentation.ui.mygoals.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import cmc.goalmate.presentation.ui.mygoals.MyGoalsScreen

const val MY_GOALS_ROUTE = "my_goals"

fun NavController.navigateToMyGoals(navOptions: NavOptions) {
    navigate(MY_GOALS_ROUTE, navOptions)
}

fun NavGraphBuilder.myGoalsScreen() {
    composable(route = MY_GOALS_ROUTE) {
        MyGoalsScreen()
    }
}

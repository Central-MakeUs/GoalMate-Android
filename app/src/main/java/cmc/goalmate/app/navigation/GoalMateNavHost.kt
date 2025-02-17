package cmc.goalmate.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import cmc.goalmate.presentation.ui.auth.navigation.authNavGraph
import cmc.goalmate.presentation.ui.comments.detail.CommentsScreen
import cmc.goalmate.presentation.ui.detail.navigation.detailNavGraph
import cmc.goalmate.presentation.ui.detail.navigation.navigateToDetail
import cmc.goalmate.presentation.ui.home.navigation.mainNavGraph
import cmc.goalmate.presentation.ui.home.navigation.navigateToHome
import cmc.goalmate.presentation.ui.progress.completed.CompletedScreen
import cmc.goalmate.presentation.ui.progress.inprogress.InProgressScreen

@Composable
fun GoalMateNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main,
    ) {
        authNavGraph(navController)
        mainNavGraph(navController)
        detailNavGraph(navController)

        composable<Screen.InProgressGoal> {
            InProgressScreen(
                navigateToGoalDetail = { navController.navigateToDetail(it) },
                navigateToComments = { navController.navigateToComments(it) },
                navigateBack = {},
            )
        }

        composable<Screen.CompletedGoal> { navBackStackEntry ->
            CompletedScreen(
                navigateToGoalDetail = { navController.navigateToDetail(it) },
                navigateToComments = { navController.navigateToComments(it) },
                navigateToHome = { navController.navigateToHome(navBackStackEntry.toRoute<Screen.CompletedGoal>()) },
                navigateBack = {},
            )
        }

        composable<Screen.Comments> {
            CommentsScreen(
                navigateBack = {},
            )
        }
    }
}

fun NavController.navigateToInProgress(goalId: Int) {
    navigate(Screen.InProgressGoal(goalId = goalId))
}

fun NavController.navigateToCompleted(goalId: Int) {
    navigate(Screen.CompletedGoal(goalId = goalId))
}

fun NavController.navigateToComments(goalId: Int) {
    navigate(Screen.Comments(goalId = goalId))
}

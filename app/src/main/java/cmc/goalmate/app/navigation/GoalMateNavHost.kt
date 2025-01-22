package cmc.goalmate.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import cmc.goalmate.presentation.ui.auth.navigation.authNavGraph
import cmc.goalmate.presentation.ui.detail.GoalDetailScreen
import cmc.goalmate.presentation.ui.detail.finish.navigation.navigateToPaymentCompleted
import cmc.goalmate.presentation.ui.detail.finish.navigation.paymentCompleted
import cmc.goalmate.presentation.ui.home.navigation.mainNavGraph
import cmc.goalmate.presentation.ui.home.navigation.navigateToHome
import cmc.goalmate.presentation.ui.progress.comments.CommentsScreen
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
        composable<Screen.Detail> {
            GoalDetailScreen(navigateToCompleted = { navController.navigateToPaymentCompleted(it) })
        }
        paymentCompleted { }

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

fun NavController.navigateToDetail(goalId: Long) {
    navigate(Screen.Detail(goalId = goalId))
}

fun NavController.navigateToInProgress(goalId: Long) {
    navigate(Screen.InProgressGoal(goalId = goalId))
}

fun NavController.navigateToCompleted(goalId: Long) {
    navigate(Screen.CompletedGoal(goalId = goalId))
}

fun NavController.navigateToComments(goalId: Long) {
    navigate(Screen.Comments(goalId = goalId))
}

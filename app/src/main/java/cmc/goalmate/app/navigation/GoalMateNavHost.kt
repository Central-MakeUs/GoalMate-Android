package cmc.goalmate.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import cmc.goalmate.presentation.ui.auth.navigation.authNavGraph
import cmc.goalmate.presentation.ui.comments.detail.CommentsDetailScreen
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

        composable<Screen.InProgressGoal> { backStackEntry ->
            val content = backStackEntry.toRoute<Screen.InProgressGoal>()
            InProgressScreen(
                goalTitle = content.goalTitle,
                navigateToGoalDetail = navController::navigateToDetail,
                navigateToComments = navController::navigateToCommentDetail,
                navigateBack = {},
            )
        }

        composable<Screen.CompletedGoal> { navBackStackEntry ->
            CompletedScreen(
                navigateToGoalDetail = navController::navigateToDetail,
                navigateToComments = navController::navigateToCommentDetail,
                navigateToHome = { navController.navigateToHome(navBackStackEntry.toRoute<Screen.CompletedGoal>()) },
                navigateBack = {},
            )
        }

        composable<Screen.CommentsDetail> { backStackEntry ->
            val content = backStackEntry.toRoute<Screen.CommentsDetail>()
            CommentsDetailScreen(
                goalTitle = content.goalTitle,
                navigateBack = {},
            )
        }
    }
}

fun NavController.navigateToInProgress(
    goalId: Int,
    title: String,
) {
    navigate(Screen.InProgressGoal(goalId = goalId, goalTitle = title))
}

fun NavController.navigateToCompleted(goalId: Int) {
    navigate(Screen.CompletedGoal(goalId = goalId))
}

fun NavController.navigateToCommentDetail(
    id: Int,
    title: String,
) {
    navigate(Screen.CommentsDetail(roomId = id, goalTitle = title))
}

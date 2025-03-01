package cmc.goalmate.app.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import cmc.goalmate.presentation.ui.auth.navigation.authNavGraph
import cmc.goalmate.presentation.ui.comments.detail.CommentsDetailScreen
import cmc.goalmate.presentation.ui.common.GoalMateWebScreen
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
        enterTransition = { fadeIn(animationSpec = tween(500)) },
        exitTransition = { fadeOut(animationSpec = tween(500)) },
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
                navigateBack = { navController.popBackStack() },
            )
        }

        composable<Screen.CompletedGoal> { navBackStackEntry ->
            CompletedScreen(
                navigateToGoalDetail = navController::navigateToDetail,
                navigateToComments = navController::navigateToCommentDetail,
                navigateToHome = { navController.navigateToHome(navBackStackEntry.toRoute<Screen.CompletedGoal>()) },
                navigateBack = { navController.popBackStack() },
            )
        }

        composable<Screen.CommentsDetail> { backStackEntry ->
            val content = backStackEntry.toRoute<Screen.CommentsDetail>()
            CommentsDetailScreen(
                goalTitle = content.goalTitle,
                navigateBack = { navController.popBackStack() },
            )
        }

        composable<Screen.WebScreen> { backStackEntry ->
            val content = backStackEntry.toRoute<Screen.WebScreen>()
            GoalMateWebScreen(
                targetUrl = content.targetUrl,
            )
        }
    }
}

package cmc.goalmate.presentation.ui.main.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
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

const val NAVIGATION_DURATION = 300

@Composable
fun GoalMateNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main,
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(NAVIGATION_DURATION, easing = LinearEasing),
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                animationSpec = tween(NAVIGATION_DURATION, easing = LinearEasing),
                towards = AnimatedContentTransitionScope.SlideDirection.End,
            )
        },
    ) {
        authNavGraph(navController)
        mainNavGraph(navController)
        detailNavGraph(navController)

        composable<Screen.InProgressGoal>(
            popExitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(NAVIGATION_DURATION, easing = LinearEasing),
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                )
            },
        ) { backStackEntry ->
            val content = backStackEntry.toRoute<Screen.InProgressGoal>()
            InProgressScreen(
                goalTitle = content.goalTitle,
                navigateToGoalDetail = navController::navigateToDetail,
                navigateToComments = navController::navigateToCommentDetail,
                navigateBack = { navController.popBackStack() },
            )
        }

        composable<Screen.CompletedGoal>(
            popExitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(NAVIGATION_DURATION, easing = LinearEasing),
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                )
            },
        ) {
            CompletedScreen(
                navigateToGoalDetail = navController::navigateToDetail,
                navigateToComments = navController::navigateToCommentDetail,
                navigateToHome = {
                    navController.popBackStack(route = Screen.Main, inclusive = true)
                    navController.navigate(Screen.Main)
                },
                navigateBack = { navController.popBackStack() },
            )
        }

        composable<Screen.CommentsDetail>(
            popExitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(NAVIGATION_DURATION, easing = LinearEasing),
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                )
            },
        ) { backStackEntry ->
            val content = backStackEntry.toRoute<Screen.CommentsDetail>()
            CommentsDetailScreen(
                goalTitle = content.goalTitle,
                navigateBack = { navController.popBackStack() },
            )
        }

        composable<Screen.WebScreen>(
            popExitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(NAVIGATION_DURATION, easing = LinearEasing),
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                )
            },
        ) { backStackEntry ->
            val content = backStackEntry.toRoute<Screen.WebScreen>()
            GoalMateWebScreen(
                targetUrl = content.targetUrl,
            )
        }
    }
}

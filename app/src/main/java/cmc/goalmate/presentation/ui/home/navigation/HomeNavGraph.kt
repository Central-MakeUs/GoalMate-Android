package cmc.goalmate.presentation.ui.home.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import cmc.goalmate.presentation.ui.auth.navigation.navigateToLogin
import cmc.goalmate.presentation.ui.comments.CommentRoomsScreen
import cmc.goalmate.presentation.ui.detail.navigation.navigateToDetail
import cmc.goalmate.presentation.ui.home.HomeScreen
import cmc.goalmate.presentation.ui.main.navigation.Screen
import cmc.goalmate.presentation.ui.main.navigation.navigateToCommentDetail
import cmc.goalmate.presentation.ui.main.navigation.navigateToCompleted
import cmc.goalmate.presentation.ui.main.navigation.navigateToInProgress
import cmc.goalmate.presentation.ui.main.navigation.navigateToWebScreen
import cmc.goalmate.presentation.ui.mygoals.MyGoalsScreen
import cmc.goalmate.presentation.ui.mypage.MyPageScreen

private const val ANIMATION_DURATION = 200
private val enterTransition = {
    fadeIn(animationSpec = tween(ANIMATION_DURATION))
}
private val exitTransition = {
    fadeOut(animationSpec = tween(ANIMATION_DURATION))
}

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation<Screen.Main>(
        startDestination = Screen.Main.Home,
    ) {
        composable<Screen.Main.Home>(
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { null },
        ) {
            HomeScreen(
                navigateToDetail = { id -> navController.navigateToDetail(id) },
            )
        }

        composable<Screen.Main.MyGoal>(
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { null },
        ) {
            MyGoalsScreen(
                navigateToCompletedGoal = navController::navigateToCompleted,
                navigateToProgressGoal = navController::navigateToInProgress,
                navigateToGoalDetail = { id -> navController.navigateToDetail(id) },
                navigateToHome = { navController.navigateToHome(Screen.Main.MyGoal) },
            )
        }

        composable<Screen.Main.Comments>(
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { null },
        ) {
            CommentRoomsScreen(
                navigateToCommentDetail = navController::navigateToCommentDetail,
                navigateToHome = { navController.navigateToHome(Screen.Main.Comments) },
            )
        }

        composable<Screen.Main.MyPage>(
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { null },
        ) {
            MyPageScreen(
                navigateToLogin = {
                    navController.navigateToLogin {
                        popUpTo<Screen.Main.MyPage> {
                            this.inclusive = true
                        }
                    }
                },
                navigateToMyGoal = { navController.navigateInBottomNav(Screen.Main.MyGoal) },
                navigateToHome = { navController.navigateToHome(Screen.Main.MyPage) },
                navigateToWebScreen = navController::navigateToWebScreen,
            )
        }
    }
}

fun NavController.navigateToHome(
    popUpToRoute: Screen,
    inclusive: Boolean = true,
) {
    navigate(Screen.Main) {
        popUpTo(popUpToRoute) {
            this.inclusive = inclusive
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateInBottomNav(screen: Screen) {
    navigate(screen) {
        popUpTo(graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

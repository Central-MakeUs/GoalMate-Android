package cmc.goalmate.presentation.ui.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import cmc.goalmate.app.navigation.Screen
import cmc.goalmate.app.navigation.navigateToCommentDetail
import cmc.goalmate.app.navigation.navigateToCompleted
import cmc.goalmate.app.navigation.navigateToInProgress
import cmc.goalmate.app.navigation.navigateToWebScreen
import cmc.goalmate.presentation.ui.auth.navigation.navigateToLogin
import cmc.goalmate.presentation.ui.comments.CommentRoomsScreen
import cmc.goalmate.presentation.ui.detail.navigation.navigateToDetail
import cmc.goalmate.presentation.ui.home.HomeScreen
import cmc.goalmate.presentation.ui.mygoals.MyGoalsScreen
import cmc.goalmate.presentation.ui.mypage.MyPageScreen

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation<Screen.Main>(
        startDestination = Screen.Main.Home,
    ) {
        composable<Screen.Main.Home> {
            HomeScreen(
                navigateToDetail = { id -> navController.navigateToDetail(id) },
            )
        }

        composable<Screen.Main.MyGoal> {
            MyGoalsScreen(
                navigateToCompletedGoal = navController::navigateToCompleted,
                navigateToProgressGoal = navController::navigateToInProgress,
                navigateToGoalDetail = { id -> navController.navigateToDetail(id) },
                navigateToHome = { navController.navigateToHome(Screen.Main.MyGoal) },
            )
        }

        composable<Screen.Main.Comments> {
            CommentRoomsScreen(
                navigateToCommentDetail = navController::navigateToCommentDetail,
                navigateToHome = { navController.navigateToHome(Screen.Main.Comments) },
            )
        }

        composable<Screen.Main.MyPage> {
            MyPageScreen(
                navigateToLogin = { navController.navigateToLogin() },
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

package cmc.goalmate.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import cmc.goalmate.presentation.ui.auth.navigation.authNavGraph
import cmc.goalmate.presentation.ui.home.HomeScreen
import cmc.goalmate.presentation.ui.mygoals.MyGoalsScreen
import cmc.goalmate.presentation.ui.mypage.MyPageScreen

@Composable
fun GoalMateNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main,
    ) {
        authNavGraph(navController)
        mainNavGraph(navController)
    }
}

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation<Screen.Main>(
        startDestination = Screen.Main.Home,
    ) {
        composable<Screen.Main.Home> {
            HomeScreen()
        }

        composable<Screen.Main.MyGoal> {
            MyGoalsScreen()
        }

        composable<Screen.Main.MyPage> {
            MyPageScreen()
        }
    }
}

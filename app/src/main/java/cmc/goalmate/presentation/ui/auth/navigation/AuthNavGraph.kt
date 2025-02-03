package cmc.goalmate.presentation.ui.auth.navigation

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import cmc.goalmate.app.navigation.Screen
import cmc.goalmate.presentation.ui.auth.LoginViewModel
import cmc.goalmate.presentation.ui.auth.login.LoginScreen
import cmc.goalmate.presentation.ui.auth.nickname.NickNameSettingScreen
import cmc.goalmate.presentation.ui.auth.welcome.WelcomeScreen
import cmc.goalmate.presentation.ui.home.navigation.navigateToHome

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation<Screen.Auth>(
        startDestination = Screen.Auth.Login,
    ) {
        composable<Screen.Auth.Login> {
            val viewModel = hiltViewModel<LoginViewModel>(
                navController.getBackStackEntry(Screen.Auth),
            )
            LoginScreen(
                navigateToNickNameSetting = {
                    navController.navigate(Screen.Auth.NickNameSetting)
                },
                viewModel = viewModel,
            )
        }

        composable<Screen.Auth.NickNameSetting> {
            val viewModel = hiltViewModel<LoginViewModel>(
                navController.getBackStackEntry(Screen.Auth),
            )
            NickNameSettingScreen(
                navigateNextPage = {
                    navController.navigate(Screen.Auth.Welcome)
                },
                modifier = Modifier,
                viewModel = viewModel,
            )
        }

        composable<Screen.Auth.Welcome> {
            val viewModel = hiltViewModel<LoginViewModel>(
                navController.getBackStackEntry(Screen.Auth),
            )
            WelcomeScreen(
                nickName = viewModel.nickName,
                navigateToNextPage = { navController.navigateToHome(Screen.Auth.Login) },
                modifier = Modifier,
            )
        }
    }
}

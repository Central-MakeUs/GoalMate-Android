package cmc.goalmate.presentation.ui.auth.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import cmc.goalmate.presentation.ui.auth.AuthViewModel
import cmc.goalmate.presentation.ui.auth.login.LoginScreen
import cmc.goalmate.presentation.ui.auth.nickname.NickNameSettingScreen
import cmc.goalmate.presentation.ui.auth.welcome.WelcomeScreen
import cmc.goalmate.presentation.ui.home.navigation.navigateToHome
import cmc.goalmate.presentation.ui.main.navigation.Screen
import cmc.goalmate.presentation.ui.main.navigation.navigateToWebScreen

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation<Screen.Auth>(
        startDestination = Screen.Auth.Login::class,
    ) {
        composable<Screen.Auth.Login> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            LoginScreen(
                navigateBack = { navController.popBackStack() },
                navigateToHome = { navController.navigateToHome(Screen.Auth.Login) },
                navigateToNickNameSetting = { navController.navigate(Screen.Auth.NickNameSetting) },
                navigateToWebScreen = navController::navigateToWebScreen,
                viewModel = viewModel,
            )
        }

        composable<Screen.Auth.NickNameSetting> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            NickNameSettingScreen(
                navigateNextPage = navController::navigateToWelcomePage,
                modifier = Modifier,
                viewModel = viewModel,
            )
        }

        composable<Screen.Auth.Welcome> { backStackEntry ->
            val nickName = backStackEntry.toRoute<Screen.Auth.Welcome>().nickName
            WelcomeScreen(
                nickName = nickName,
                navigateToNextPage = { navController.navigateToHome(Screen.Auth.Login) },
                modifier = Modifier,
            )
        }
    }
}

fun NavController.navigateToLogin() {
    navigate(Screen.Auth)
}

fun NavController.navigateToWelcomePage(nickName: String) {
    navigate(Screen.Auth.Welcome(nickName))
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}

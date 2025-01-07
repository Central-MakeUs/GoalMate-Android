package cmc.goalmate.presentation.ui.mypage.navgation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import cmc.goalmate.presentation.ui.mypage.MyPageScreen

const val MY_PAGE_ROUTE = "my_page"

fun NavController.navigateToMyPage(navOptions: NavOptions) {
    navigate(MY_PAGE_ROUTE, navOptions)
}

fun NavGraphBuilder.myPageScreen() {
    composable(route = MY_PAGE_ROUTE) {
        MyPageScreen()
    }
}

package cmc.goalmate.presentation.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.R
import cmc.goalmate.presentation.components.HeaderTitle
import cmc.goalmate.presentation.ui.common.UserState
import cmc.goalmate.presentation.ui.mypage.model.MenuItemData

@Composable
fun MyPageScreen(
    navigateToLogin: () -> Unit,
    navigateToMyGoal: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val commonMenuItems = listOf(
        MenuItemData("자주 묻는 질문") { },
        MenuItemData("개인 정보 처리 방침") { },
        MenuItemData("이용약관") { },
    )

    val loggedInMenuItems = listOf(
        MenuItemData("탈퇴하기") { },
        MenuItemData("로그아웃") { },
    )

    val menuItems = commonMenuItems + if (state.userGoalsState is UserState.LoggedIn) loggedInMenuItems else emptyList()

    Column {
        HeaderTitle(
            title = stringResource(R.string.my_page_title),
            modifier = Modifier.fillMaxWidth(),
        )
        MyPageContent(
            userState = state.userGoalsState,
            menuItems = menuItems,
            editNickName = {},
            navigateToMyGoals = {},
            navigateToLogin = navigateToLogin,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

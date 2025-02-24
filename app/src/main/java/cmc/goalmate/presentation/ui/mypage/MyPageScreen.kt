package cmc.goalmate.presentation.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.R
import cmc.goalmate.presentation.components.HeaderTitle
import cmc.goalmate.presentation.ui.common.WebScreenUrl
import cmc.goalmate.presentation.ui.mypage.model.MenuItemUiModel
import cmc.goalmate.presentation.ui.util.ObserveAsEvent

@Composable
fun MyPageScreen(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToMyGoal: () -> Unit,
    navigateToWebScreen: (WebScreenUrl) -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.event) { event ->
        when (event) {
            MyPageEvent.ShowFAQ -> {
                navigateToWebScreen(WebScreenUrl.FAQ)
            }
            MyPageEvent.ShowPrivacyPolicy -> {
                navigateToWebScreen(WebScreenUrl.PrivacyPolicy)
            }
            MyPageEvent.ShowTermsOfService -> {
                navigateToWebScreen(WebScreenUrl.TermsOfService)
            }
            MyPageEvent.SuccessDeleteAccount -> {
                navigateToHome()
            }
            MyPageEvent.SuccessLogout -> {
                navigateToHome()
            }
            MyPageEvent.EditNickName -> {}
            MyPageEvent.NeedLogin -> {
                navigateToLogin()
            }
        }
    }

    Column {
        HeaderTitle(
            title = stringResource(R.string.my_page_title),
            modifier = Modifier.fillMaxWidth(),
        )
        MyPageContent(
            userState = state,
            menuItems = MenuItemUiModel.getMenuItems(state.isLoggedIn()),
            navigateToMyGoals = navigateToMyGoal,
            onAction = viewModel::onAction,
            modifier = Modifier.weight(1f),
        )
    }
}

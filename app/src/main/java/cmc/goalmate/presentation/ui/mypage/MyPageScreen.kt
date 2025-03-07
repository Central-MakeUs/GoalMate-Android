package cmc.goalmate.presentation.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.R
import cmc.goalmate.presentation.components.HeaderTitle
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.ui.common.WebScreenUrl
import cmc.goalmate.presentation.ui.mypage.components.NickNameEditContent
import cmc.goalmate.presentation.ui.mypage.model.MenuItemUiModel
import cmc.goalmate.presentation.ui.util.ObserveAsEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToMyGoal: () -> Unit,
    navigateToWebScreen: (WebScreenUrl) -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        snapshotFlow { sheetState.isVisible }
            .distinctUntilChanged()
            .collectLatest { isVisible ->
                if (isVisible) {
                    delay(300)
                    focusRequester.requestFocus()
                }
            }
    }

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
            MyPageEvent.NeedLogin -> {
                navigateToLogin()
            }
            MyPageEvent.EditNickName -> {
                showBottomSheet = true
            }
            MyPageEvent.SuccessChangeNickName -> {
                coroutineScope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
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
            modifier = Modifier.weight(1f).verticalScroll(scrollState),
        )
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.goalMateColors.background,
            tonalElevation = 0.dp,
        ) {
            NickNameEditContent(
                nickNameText = viewModel.nickName,
                nickNameTextState = state.successData().inputTextState,
                helperText = state.successData().helperText,
                canCheckDuplicate = state.successData().canCheckDuplication,
                onAction = viewModel::onAction,
                isConfirmButtonEnabled = state.successData().canConfirmNickNameChanged,
                focusRequester = focusRequester,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = GoalMateDimens.HorizontalPadding,
                        vertical = GoalMateDimens.BottomMargin,
                    ),
            )
        }
    }
}

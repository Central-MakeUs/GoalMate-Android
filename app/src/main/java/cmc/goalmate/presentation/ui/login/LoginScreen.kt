package cmc.goalmate.presentation.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { LoginStep.entries.size })
    val coroutineScope = rememberCoroutineScope()

    fun moveToNextPage() {
        val nextPage = pagerState.currentPage + 1
        if (nextPage < pagerState.pageCount) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    LoginContent(
        state = state,
        nickName = viewModel.nickName,
        onAction = { action ->
            when (action) {
                LoginAction.KakaoLogin, LoginAction.CompleteNicknameSetup -> {
                    moveToNextPage()
                }

                else -> Unit
            }
            viewModel.onAction(action)
        },
        completeLogin = navigateToHome,
        pagerState = pagerState,
        modifier = modifier
            .background(color = MaterialTheme.goalMateColors.background)
            .imePadding()
            .systemBarsPadding(),
    )
}

@Composable
fun LoginContent(
    state: LoginUiState,
    nickName: String,
    onAction: (LoginAction) -> Unit,
    completeLogin: () -> Unit,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.size(40.dp))

        StepProgressBar(steps = state.loginSteps)

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth().weight(1f),
            userScrollEnabled = false,
        ) { page ->
            when (page) {
                0 -> SingUpScreen(
                    onLoginButtonClicked = { onAction(LoginAction.KakaoLogin) },
                    modifier = Modifier.fillMaxWidth(),
                )

                1 -> NickNameSettingScreen(
                    text = nickName,
                    onTextChanged = { onAction(LoginAction.SetNickName(it)) },
                    textValidationState = state.validationState,
                    helperText = state.helperText,
                    isDuplicationCheckEnabled = state.isDuplicationCheckEnabled,
                    isNextStepEnabled = state.isNextStepEnabled,
                    onDuplicationCheckButtonClicked = { onAction(LoginAction.CheckDuplication) },
                    onCompletedButtonClicked = { onAction(LoginAction.CompleteNicknameSetup) },
                    modifier = Modifier,
                )

                2 -> CompletedScreen(
                    nickName = nickName,
                    onCompletedButtonClicked = completeLogin,
                    modifier = Modifier,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    GoalMateTheme {
        LoginScreen(
            navigateToHome = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}

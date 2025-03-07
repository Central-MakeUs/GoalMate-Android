package cmc.goalmate.presentation.ui.auth.nickname

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.R
import cmc.goalmate.presentation.components.ButtonSize
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.components.GoalMateTextField
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.auth.AuthAction
import cmc.goalmate.presentation.ui.auth.AuthEvent
import cmc.goalmate.presentation.ui.auth.AuthUiState
import cmc.goalmate.presentation.ui.auth.AuthViewModel
import cmc.goalmate.presentation.ui.auth.component.StepProgressBar
import cmc.goalmate.presentation.ui.auth.secondStep
import cmc.goalmate.presentation.ui.util.ObserveAsEvent

@Composable
fun NickNameSettingScreen(
    navigateNextPage: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.authEvent) { event ->
        when (event) {
            is AuthEvent.NavigateToCompleted -> {
                navigateNextPage(event.confirmedNickName)
            }

            else -> Unit
        }
    }

    NickNameSettingContent(
        text = viewModel.nickName,
        state = state,
        onAction = viewModel::onAction,
        onCompletedButtonClicked = { viewModel.onAction(AuthAction.CompleteNicknameSetup) },
        modifier = modifier
            .background(MaterialTheme.goalMateColors.background)
            .fillMaxSize()
            .padding(horizontal = GoalMateDimens.HorizontalPadding),
    )
}

@Composable
fun NickNameSettingContent(
    text: String,
    state: AuthUiState,
    onAction: (AuthAction) -> Unit,
    onCompletedButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        StepProgressBar(steps = secondStep)

        Spacer(modifier = Modifier.size(68.dp))

        Text(
            text = stringResource(R.string.login_nick_name_setting_title),
            style = MaterialTheme.goalMateTypography.subtitleMedium,
            color = MaterialTheme.goalMateColors.onBackground,
        )

        Spacer(modifier = Modifier.size(44.dp))

        GoalMateTextField(
            value = text,
            defaultValue = state.defaultNickName,
            onValueChange = { onAction(AuthAction.SetNickName(it)) },
            canCheckDuplicate = state.isDuplicationCheckEnabled,
            onDuplicateCheck = { onAction(AuthAction.CheckDuplication) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            inputTextState = state.validationState,
            helperText = state.helperText,
        )

        Spacer(modifier = Modifier.weight(1f))

        GoalMateButton(
            content = stringResource(R.string.login_nick_name_btn),
            onClick = {
                focusManager.clearFocus()
                onCompletedButtonClicked()
            },
            buttonSize = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth(),
            enabled = state.isNextStepButtonEnabled(text),
        )

        Spacer(modifier = Modifier.size(GoalMateDimens.BottomMargin))
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun NickNameSettingScreenPreview() {
    GoalMateTheme {
        NickNameSettingContent(
            text = "UserNickname",
            state = AuthUiState.initialLoginUiState(),
            onAction = {},
            onCompletedButtonClicked = { },
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = GoalMateDimens.HorizontalPadding)
                .navigationBarsPadding(),
        )
    }
}

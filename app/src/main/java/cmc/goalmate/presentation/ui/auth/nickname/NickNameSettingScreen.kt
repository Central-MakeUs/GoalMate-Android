package cmc.goalmate.presentation.ui.auth.nickname

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
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
import cmc.goalmate.presentation.components.InputTextState
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.auth.LoginAction
import cmc.goalmate.presentation.ui.auth.LoginUiState
import cmc.goalmate.presentation.ui.auth.LoginViewModel
import cmc.goalmate.presentation.ui.auth.component.StepProgressBar
import cmc.goalmate.presentation.ui.auth.secondStep
import kotlinx.coroutines.launch

@Composable
fun NickNameSettingScreen(
    navigateNextPage: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    NickNameSettingContent(
        text = viewModel.nickName,
        state = state,
        onAction = viewModel::onAction,
        onCompletedButtonClicked = navigateNextPage,
        modifier = modifier.fillMaxSize()
            .padding(horizontal = GoalMateDimens.HorizontalPadding),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NickNameSettingContent(
    text: String,
    state: LoginUiState,
    onAction: (LoginAction) -> Unit,
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

    if (state.validationState == InputTextState.Success) {
        LaunchedEffect(Unit) {
            focusManager.clearFocus()
        }
    }

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val termOptions = remember { TermOption.DEFAULT.toMutableStateList() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        StepProgressBar(steps = secondStep)
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.login_nick_name_setting_title),
            style = MaterialTheme.goalMateTypography.subtitleMedium,
            color = MaterialTheme.goalMateColors.onBackground,
        )

        Spacer(modifier = Modifier.size(44.dp))

        GoalMateTextField(
            value = text,
            onValueChange = { onAction(LoginAction.SetNickName(it)) },
            canCheckDuplicate = state.isDuplicationCheckEnabled,
            onDuplicateCheck = { onAction(LoginAction.CheckDuplication) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            inputTextState = state.validationState,
            helperText = state.helperText,
        )

        Spacer(modifier = Modifier.weight(1f))

        GoalMateButton(
            content = stringResource(R.string.login_nick_name_btn),
            onClick = { showBottomSheet = true },
            buttonSize = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth(),
            enabled = state.isNextStepEnabled,
        )

        Spacer(modifier = Modifier.size(GoalMateDimens.BottomMargin))
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
        ) {
            TermsOfServiceScreen(
                termOptions = termOptions,
                isButtonEnabled = termOptions.all { it.isChecked },
                onCompletedButtonClicked = {
                    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                        onCompletedButtonClicked()
                    }
                },
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun NickNameSettingScreenPreview() {
    GoalMateTheme {
        NickNameSettingContent(
            text = "UserNickname",
            state = LoginUiState.initialLoginUiState(),
            onAction = {},
            onCompletedButtonClicked = { },
            modifier = Modifier,
        )
    }
}

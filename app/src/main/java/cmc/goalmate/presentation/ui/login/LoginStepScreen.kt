package cmc.goalmate.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.ButtonSize
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.components.GoalMateTextField
import cmc.goalmate.presentation.components.InputTextState
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun SingUpScreen(
    onLoginButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Box {
            Image(
                painter = painterResource(R.drawable.image_onboarding),
                contentDescription = stringResource(R.string.login_onboarding_message),
                modifier = Modifier.size(320.dp, 300.dp),
            )
            Text(
                text = stringResource(R.string.login_onboarding_message),
                style = MaterialTheme.goalMateTypography.subtitleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 17.dp),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.kakao_login_image),
            contentDescription = "kakao_login",
            modifier = Modifier
                .clickable(
                    onClick = onLoginButtonClicked,
                ),
        )
        Spacer(modifier = Modifier.size(GoalMateDimens.BottomMargin))
    }
}

@Composable
fun NickNameSettingScreen(
    text: String,
    onTextChanged: (String) -> Unit,
    textValidationState: InputTextState,
    helperText: String,
    isDuplicationCheckEnabled: Boolean,
    isNextStepEnabled: Boolean,
    onDuplicationCheckButtonClicked: () -> Unit,
    onCompletedButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(horizontal = GoalMateDimens.HorizontalPadding),
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(R.string.login_nick_name_setting_title),
            style = MaterialTheme.goalMateTypography.subtitleSmall,
            color = MaterialTheme.goalMateColors.onBackground,
        )

        Spacer(modifier = Modifier.size(44.dp))

        GoalMateTextField(
            value = text,
            onValueChange = onTextChanged,
            canCheckDuplicate = isDuplicationCheckEnabled,
            onDuplicateCheck = onDuplicationCheckButtonClicked,
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
            inputTextState = textValidationState,
            helperText = helperText,
        )

        Spacer(modifier = Modifier.weight(1f))

        GoalMateButton(
            content = stringResource(R.string.login_nick_name_btn),
            onClick = onCompletedButtonClicked,
            buttonSize = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth(),
            enabled = isNextStepEnabled,
        )

        Spacer(modifier = Modifier.size(GoalMateDimens.BottomMargin))
    }
}

@Composable
fun CompletedScreen(
    nickName: String,
    onCompletedButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = GoalMateDimens.HorizontalPadding),
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Box {
            Image(
                painter = painterResource(R.drawable.image_welcome),
                contentDescription = stringResource(R.string.login_greeting_message, nickName),
                modifier = Modifier.size(width = 320.dp, height = 300.dp),
            )

            Text(
                text = stringResource(R.string.login_greeting_message, nickName),
                style = MaterialTheme.goalMateTypography.subtitleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 40.5.dp),
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        GoalMateButton(
            content = "시작하기",
            onClick = onCompletedButtonClicked,
            buttonSize = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.size(GoalMateDimens.BottomMargin))
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun NickNameSettingScreenPreview() {
    GoalMateTheme {
        NickNameSettingScreen(
            text = "UserNickname",
            onTextChanged = { },
            textValidationState = InputTextState.None,
            helperText = "닉네임은 2~10자 사이여야 합니다.",
            isDuplicationCheckEnabled = true,
            isNextStepEnabled = true,
            onDuplicationCheckButtonClicked = { },
            onCompletedButtonClicked = { },
            modifier = Modifier.background(color = Color.White).navigationBarsPadding(),
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun CompletedScreenPreview() {
    GoalMateTheme {
        CompletedScreen(
            nickName = "예니",
            onCompletedButtonClicked = { },
            modifier = Modifier.background(color = Color.White).navigationBarsPadding(),
        )
    }
}

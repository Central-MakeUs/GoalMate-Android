package cmc.goalmate.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.ButtonSize
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.components.GoalMateTextField
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.color.Grey100
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
        Box(
            modifier = Modifier
                .size(320.dp, 300.dp)
                .background(color = Grey100),
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.kakao_login_image),
            contentDescription = "kakao_login",
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = onLoginButtonClicked,
                ),
        )
    }
}

@Composable
fun NickNameSettingScreen(
    text: String,
    onTextChanged: (String) -> Unit,
    onDuplicationCheckButtonClicked: () -> Unit,
    onCompletedButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(horizontal = GoalMateDimens.HorizontalPadding),
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
            canCheckDuplicate = false,
            onDuplicateCheck = onDuplicationCheckButtonClicked,
            modifier = Modifier
                .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.weight(1f))
        GoalMateButton(
            content = stringResource(R.string.login_nick_name_btn),
            onClick = onCompletedButtonClicked,
            buttonSize = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth(),
        )
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
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.login_greeting_message, nickName),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.weight(1f))
        GoalMateButton(
            content = "시작하기",
            onClick = onCompletedButtonClicked,
            buttonSize = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

package cmc.goalmate.presentation.ui.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.White
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.auth.LoginAction
import cmc.goalmate.presentation.ui.auth.LoginViewModel
import cmc.goalmate.presentation.ui.auth.component.StepProgressBar
import cmc.goalmate.presentation.ui.auth.firstStep
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navigateToNickNameSetting: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    LoginContent(
        viewModel::onAction,
        onCompletedButtonClicked = navigateToNickNameSetting,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginContent(
    onAction: (LoginAction) -> Unit,
    onCompletedButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LoginMainContent(
        onLoginButtonClicked = { onAction(LoginAction.KakaoLogin) },
        modifier.fillMaxWidth(),
    )

    if (showBottomSheet) {
        LoginTermBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet = false },
        ) {
            coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    showBottomSheet = false
                }
                onCompletedButtonClicked()
            }
        }
    }
}

@Composable
private fun LoginMainContent(
    onLoginButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = GoalMateDimens.HorizontalPadding),
    ) {
        StepProgressBar(steps = firstStep)
        Spacer(modifier = Modifier.weight(1f))
        Box {
            Image(
                painter = painterResource(R.drawable.image_login),
                contentDescription = stringResource(R.string.login_onboarding_message),
                modifier = Modifier.size(320.dp, 300.dp),
            )
            Text(
                text = stringResource(R.string.login_onboarding_message),
                style = MaterialTheme.goalMateTypography.subtitle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 17.dp),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginTermBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onCompletedButtonClicked: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.goalMateColors.background,
        tonalElevation = 0.dp,
    ) {
        TermsOfServiceScreen(
            onCompletedButtonClicked = onCompletedButtonClicked,
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun LoginPreview() {
    GoalMateTheme {
        LoginContent(
            {},
            {},
            modifier = Modifier.background(White),
        )
    }
}

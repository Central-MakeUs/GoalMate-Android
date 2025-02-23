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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cmc.goalmate.R
import cmc.goalmate.presentation.components.AppBarWithBackButton
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.White
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.auth.AuthAction
import cmc.goalmate.presentation.ui.auth.AuthEvent
import cmc.goalmate.presentation.ui.auth.AuthViewModel
import cmc.goalmate.presentation.ui.auth.component.StepProgressBar
import cmc.goalmate.presentation.ui.auth.firstStep
import cmc.goalmate.presentation.ui.util.ObserveAsEvent
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToNickNameSetting: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    ObserveAsEvent(viewModel.authEvent) { event ->
        when (event) {
            AuthEvent.NavigateToHome -> navigateToHome()
            AuthEvent.NavigateToNickNameSetting -> navigateToNickNameSetting()
            AuthEvent.GetAgreeWithTerms -> {
                showBottomSheet = true
            }
            else -> Unit
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AppBarWithBackButton(onBackButtonClicked = navigateBack, iconRes = R.drawable.icon_cancel)
        LoginContent(
            onLoginButtonClicked = {
                coroutineScope.launch {
                    val token = UserApiClient.loginWithKakao(context)
                    viewModel.onAction(AuthAction.KakaoLogin(token.idToken))
                }
            },
            modifier = modifier.fillMaxWidth(),
        )
    }

    if (showBottomSheet) {
        LoginTermBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet = false },
        ) {
            coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    showBottomSheet = false
                }
                viewModel.onAction(AuthAction.AgreeTermsOfService)
            }
        }
    }
}

@Composable
private fun LoginContent(
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
                .clip(RoundedCornerShape(30.dp))
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
@Preview(showBackground = true)
private fun LoginPreview() {
    GoalMateTheme {
        LoginContent(
            {},
            modifier = Modifier.background(White),
        )
    }
}

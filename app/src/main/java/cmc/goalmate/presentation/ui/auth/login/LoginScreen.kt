package cmc.goalmate.presentation.ui.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.auth.component.StepProgressBar
import cmc.goalmate.presentation.ui.auth.firstStep

@Composable
fun LoginScreen(
    onLoginButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth().padding(horizontal = GoalMateDimens.HorizontalPadding),
    ) {
        StepProgressBar(steps = firstStep)
        Spacer(modifier = Modifier.weight(1f))
        Box {
            Image(
                painter = painterResource(R.drawable.image_onboarding),
                contentDescription = stringResource(R.string.login_onboarding_message),
                modifier = Modifier.size(320.dp, 300.dp),
            )
            Text(
                text = stringResource(R.string.login_onboarding_message),
                style = MaterialTheme.goalMateTypography.subtitleMedium,
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
@Preview(showBackground = true, showSystemUi = true)
private fun LoginPreview() {
    GoalMateTheme {
        LoginScreen(
            onLoginButtonClicked = {},
            modifier = Modifier,
        )
    }
}

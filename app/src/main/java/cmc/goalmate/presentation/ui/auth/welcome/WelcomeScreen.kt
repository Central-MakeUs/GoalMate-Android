package cmc.goalmate.presentation.ui.auth.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.ButtonSize
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.auth.component.StepProgressBar
import cmc.goalmate.presentation.ui.auth.lastStep

@Composable
fun WelcomeScreen(
    nickName: String,
    navigateToNextPage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = GoalMateDimens.HorizontalPadding),
    ) {
        StepProgressBar(steps = lastStep)
        Spacer(modifier = Modifier.size(83.dp))
        Box {
            Image(
                painter = painterResource(R.drawable.image_login_completed),
                contentDescription = stringResource(R.string.login_greeting_message, nickName),
                modifier = Modifier.size(width = 320.dp, height = 300.dp),
            )

            Text(
                text = stringResource(R.string.login_greeting_message, nickName),
                style = MaterialTheme.goalMateTypography.subtitleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 40.5.dp),
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        GoalMateButton(
            content = "시작하기",
            onClick = navigateToNextPage,
            buttonSize = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.size(GoalMateDimens.BottomMargin))
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun WelcomeScreenPreview() {
    GoalMateTheme {
        WelcomeScreen(
            nickName = "예니",
            navigateToNextPage = { },
            modifier = Modifier.background(color = Color.White).navigationBarsPadding(),
        )
    }
}

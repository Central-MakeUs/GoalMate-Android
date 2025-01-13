package cmc.goalmate.presentation.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(modifier: Modifier) {
    var loginSteps by remember { mutableStateOf(createInitialLoginSteps()) }
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { loginSteps.size })
    val coroutineScope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }

    fun moveToNextPage() {
        val nextPage = pagerState.currentPage + 1
        if (nextPage < pagerState.pageCount) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.size(40.dp))

        StepProgressBar(steps = loginSteps)

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            userScrollEnabled = false,
        ) { page ->
            when (page) {
                0 -> SingUpScreen(
                    onLoginButtonClicked = { moveToNextPage() },
                    modifier = Modifier.fillMaxSize(),
                )

                1 -> NickNameSettingScreen(
                    text = text,
                    onTextChanged = { text = it },
                    onDuplicationCheckButtonClicked = {},
                    onCompletedButtonClicked = { moveToNextPage() },
                    modifier = Modifier.fillMaxSize(),
                )

                2 -> CompletedScreen(
                    nickName = "예니",
                    onCompletedButtonClicked = { moveToNextPage() },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        Spacer(modifier = Modifier.size(GoalMateDimens.BottomMargin))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    GoalMateTheme {
        LoginScreen(modifier = Modifier.fillMaxSize())
    }
}

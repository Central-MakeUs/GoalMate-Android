package cmc.goalmate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun ErrorScreen(
    onRetryButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.background(color = MaterialTheme.goalMateColors.background)) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Text(
                text = "문제가 생겨\n화면을 불러오지 못했어요",
                textAlign = TextAlign.Center,
                color = MaterialTheme.goalMateColors.onBackground,
                style = MaterialTheme.goalMateTypography.body,
            )
            GoalMateButton(
                content = "다시 시도하기",
                onClick = onRetryButtonClicked,
                buttonSize = ButtonSize.SMALL,
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ErrorScreenPreview() {
    GoalMateTheme {
        ErrorScreen(
            onRetryButtonClicked = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}

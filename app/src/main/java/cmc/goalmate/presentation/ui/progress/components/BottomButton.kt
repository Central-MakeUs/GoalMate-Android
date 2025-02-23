package cmc.goalmate.presentation.ui.progress.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors

@Composable
fun ProgressBottomButton(
    buttonText: String,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.goalMateColors.background.copy(alpha = 0f),
                            MaterialTheme.goalMateColors.background,
                        ),
                        startY = 0f,
                    ),
                )
        )

        GoalMateButton(
            content = buttonText,
            onClick = onClicked,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.goalMateColors.background)
                .padding(horizontal = GoalMateDimens.HorizontalPadding)
                .padding(bottom = 15.dp),
        )
    }
}

@Composable
@Preview
private fun ProgressBottomButtonPreview() {
    GoalMateTheme {
        ProgressBottomButton(
            buttonText = "멘토 코멘트 받으러 가기",
            onClicked = {},
            modifier = Modifier,
        )
    }
}

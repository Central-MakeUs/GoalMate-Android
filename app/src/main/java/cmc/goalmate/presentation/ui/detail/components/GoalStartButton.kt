package cmc.goalmate.presentation.ui.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors

@Composable
fun GoalStartButton(
    isLoggedIn: Boolean,
    isEnabled: Boolean,
    availableSeatCount: Int,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val buttonText =
        if (!isLoggedIn && isEnabled) {
            stringResource(R.string.goal_detail_start_login_button)
        } else {
            stringResource(
                R.string.goal_detail_start_button,
            )
        }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.goalMateColors.background.copy(alpha = 0f),
                            MaterialTheme.goalMateColors.background,
                        ),
                        startY = 0f,
                    ),
                )
                .padding(bottom = 10.dp)
                .heightIn(min = 20.dp),
        ) {
            if (isEnabled) {
                FreeEntryCountTag(
                    availableSeatCount = availableSeatCount,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }

        GoalMateButton(
            content = buttonText,
            onClick = onClicked,
            enabled = isEnabled,
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
private fun GoalStartButtonPreview() {
    GoalMateTheme {
        GoalStartButton(
            availableSeatCount = 2,
            isLoggedIn = true,
            isEnabled = false,
            onClicked = {},
            modifier = Modifier,
        )
    }
}

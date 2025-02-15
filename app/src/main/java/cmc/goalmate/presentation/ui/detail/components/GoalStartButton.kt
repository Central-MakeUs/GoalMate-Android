package cmc.goalmate.presentation.ui.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme

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
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        if (isEnabled) {
            FreeEntryCountTag(availableSeatCount = availableSeatCount)
        }

        GoalMateButton(
            content = buttonText,
            onClick = onClicked,
            enabled = isEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = GoalMateDimens.HorizontalPadding),
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
            isEnabled = true,
            onClicked = {},
            modifier = Modifier,
        )
    }
}

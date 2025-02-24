package cmc.goalmate.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun EmptyGoalContents(
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.my_goals_empty_message),
            textAlign = TextAlign.Center,
            style = MaterialTheme.goalMateTypography.body,
        )
        Spacer(modifier = Modifier.size(24.dp))
        GoalMateButton(
            content = stringResource(R.string.my_goals_empty_button),
            onClick = onButtonClicked,
            buttonSize = ButtonSize.LARGE,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun EmptyGoalContentsPreview() {
    GoalMateTheme {
        EmptyGoalContents(
            onButtonClicked = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}

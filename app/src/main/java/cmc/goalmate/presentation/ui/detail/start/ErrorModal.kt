package cmc.goalmate.presentation.ui.detail.start

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.GoalMateImage
import cmc.goalmate.presentation.theme.GoalMateTheme

@Composable
fun ErrorContent(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GoalMateImage(
            image = R.drawable.icon_warn,
        )
        Text(text = stringResource(R.string.goal_detail_unavailable))
        Spacer(Modifier.size(12.dp))
    }
}

@Composable
@Preview(showBackground = true)
private fun GoalStartScreenPreview() {
    GoalMateTheme {
        ErrorContent()
    }
}

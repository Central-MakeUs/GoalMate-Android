package cmc.goalmate.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors

@Composable
fun ThinDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        thickness = 1.dp,
        color = MaterialTheme.goalMateColors.thinDivider,
    )
}

@Composable
fun ThickDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        thickness = 16.dp,
        color = MaterialTheme.goalMateColors.thickDivider,
    )
}

@Composable
@Preview
fun ThickDividerPreview() {
    GoalMateTheme {
        ThickDivider(modifier = Modifier.fillMaxWidth())
    }
}

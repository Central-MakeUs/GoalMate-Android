package cmc.goalmate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.White
import cmc.goalmate.presentation.theme.goalMateColors

@Composable
fun ThinDivider(
    modifier: Modifier = Modifier,
    paddingTop: Dp = 0.dp,
    paddingBottom: Dp = 0.dp,
) {
    HorizontalDivider(
        modifier = modifier.padding(top = paddingTop, bottom = paddingBottom),
        thickness = 1.dp,
        color = MaterialTheme.goalMateColors.thinDivider,
    )
}

@Composable
fun ThickDivider(
    modifier: Modifier = Modifier,
    paddingTop: Dp = 0.dp,
    paddingBottom: Dp = 0.dp,
) {
    HorizontalDivider(
        modifier = modifier.padding(top = paddingTop, bottom = paddingBottom),
        thickness = 16.dp,
        color = MaterialTheme.goalMateColors.thickDivider,
    )
}

@Composable
@Preview
private fun ThickDividerPreview() {
    GoalMateTheme {
        ThickDivider(
            modifier = Modifier.fillMaxWidth().background(White),
            paddingBottom = 30.dp,
        )
    }
}

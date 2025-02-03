package cmc.goalmate.presentation.ui.progress.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun Subtitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = MaterialTheme.goalMateTypography.subtitleSmall,
        color = MaterialTheme.goalMateColors.onSecondary02,
        modifier = modifier,
    )
}

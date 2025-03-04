package cmc.goalmate.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = "문제가 생겨\n화면을 불러오지 못했어요",
            textAlign = TextAlign.Center,
            color = MaterialTheme.goalMateColors.onBackground,
            style = MaterialTheme.goalMateTypography.body,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

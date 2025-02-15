package cmc.goalmate.presentation.ui.mypage.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun GoalCountItem(
    count: String,
    tag: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        Text(
            text = count,
            style = MaterialTheme.goalMateTypography.subtitle,
            color = MaterialTheme.goalMateColors.onBackground,
        )
        Text(
            text = tag,
            style = MaterialTheme.goalMateTypography.body,
            color = MaterialTheme.goalMateColors.onSurface,
        )
    }
}

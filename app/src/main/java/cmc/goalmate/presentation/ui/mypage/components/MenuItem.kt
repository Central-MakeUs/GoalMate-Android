package cmc.goalmate.presentation.ui.mypage.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.mypage.model.MenuItemUiModel

@Composable
fun MenuItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = MaterialTheme.goalMateTypography.body,
        color = MaterialTheme.goalMateColors.onBackground,
        modifier = modifier
            .clickable { onClick() }
            .padding(horizontal = GoalMateDimens.HorizontalPadding)
            .padding(vertical = 11.dp),
    )
}

@Composable
@Preview(showBackground = true)
private fun MenuItemPreview() {
    val target = MenuItemUiModel.DeleteAccount
    GoalMateTheme {
        MenuItem(
            title = target.title,
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

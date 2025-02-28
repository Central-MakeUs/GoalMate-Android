package cmc.goalmate.presentation.ui.comments.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun GoalMateDropDownMenu(
    isDropDownMenuExpanded: Boolean,
    onDismissRequest: () -> Unit,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenu(
        expanded = isDropDownMenuExpanded,
        onDismissRequest = { onDismissRequest() },
        containerColor = MaterialTheme.goalMateColors.background,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = "수정",
                    color = MaterialTheme.goalMateColors.onBackground,
                    style = MaterialTheme.goalMateTypography.bodySmall,
                )
            },
            onClick = {
                onEditClicked()
                onDismissRequest()
            },
        )
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.goalMateColors.outline)
        DropdownMenuItem(
            text = {
                Text(
                    text = "삭제",
                    color = MaterialTheme.goalMateColors.onError,
                    style = MaterialTheme.goalMateTypography.bodySmall,
                )
            },
            onClick = {
                onDeleteClicked()
                onDismissRequest()
            },
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun GoalMateDropDownMenuPreview() {
    var isDropDownMenuExpanded by remember { mutableStateOf(true) }
    GoalMateTheme {
        GoalMateDropDownMenu(
            isDropDownMenuExpanded = isDropDownMenuExpanded,
            onDismissRequest = { isDropDownMenuExpanded = false },
            {},
            {},
        )
    }
}

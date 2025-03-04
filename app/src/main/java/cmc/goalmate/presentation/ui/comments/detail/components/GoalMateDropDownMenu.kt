package cmc.goalmate.presentation.ui.comments.detail.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.util.crop

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
        offset = DpOffset(x = 20.dp, y = 0.dp),
        shape = RoundedCornerShape(14.dp),
        modifier = modifier
            .width(GoalMateDimens.PopupMenuWidth)
            .crop(vertical = 8.dp),
    ) {
        GoalMateDropDownMenuItem(
            label = "수정",
            labelColor = MaterialTheme.goalMateColors.onBackground,
            onClicked = {
                onEditClicked()
                onDismissRequest()
            },
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.goalMateColors.outline,
        )

        GoalMateDropDownMenuItem(
            label = "삭제",
            labelColor = MaterialTheme.goalMateColors.onError,
            onClicked = {
                onDeleteClicked()
                onDismissRequest()
            },
        )
    }
}

@Composable
private fun GoalMateDropDownMenuItem(
    label: String,
    labelColor: Color,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val itemTextStyle = MaterialTheme.goalMateTypography.bodySmall.copy(lineHeight = 14.sp)

    DropdownMenuItem(
        text = {
            Text(
                text = label,
                color = labelColor,
                style = itemTextStyle,
            )
        },
        onClick = onClicked,
        contentPadding = PaddingValues(horizontal = GoalMateDimens.PopupMenuItemPadding),
        modifier = modifier.height(GoalMateDimens.PopupMenuItemHeight),
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun GoalMateDropDownMenuPreview() {
    var isDropDownMenuExpanded by remember { mutableStateOf(true) }
    GoalMateTheme {
        GoalMateDropDownMenu(
            isDropDownMenuExpanded = isDropDownMenuExpanded,
            onDismissRequest = { isDropDownMenuExpanded = false },
            {},
            {},
            modifier = Modifier,
        )
    }
}

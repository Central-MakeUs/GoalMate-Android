package cmc.goalmate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors

@Composable
fun GoalMateCheckBox(
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentColor =
        if (isChecked) MaterialTheme.goalMateColors.primary else MaterialTheme.goalMateColors.checkboxBackground

    Box(
        modifier = modifier
            .size(18.dp)
            .background(color = contentColor, RoundedCornerShape(4.dp))
            .clickable { onCheckedChange() },
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
        ) {
            if (isChecked) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_checkbox_check),
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
@Preview
private fun GoalMateCheckButtonPreview() {
    var checked by remember { mutableStateOf(true) }
    GoalMateTheme {
        GoalMateCheckBox(
            isChecked = checked,
            onCheckedChange = { checked = !checked },
        )
    }
}

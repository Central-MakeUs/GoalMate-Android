package cmc.goalmate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun GoalMateCheckBox(
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {
    val contentColor =
        if (isChecked) {
            MaterialTheme.goalMateColors.primary
        } else {
            MaterialTheme.goalMateColors.checkboxBackground
        }
    val hapticFeedback = LocalHapticFeedback.current

    Box(
        modifier = modifier
            .size(GoalMateDimens.CheckBoxSize)
            .clickable(
                enabled = isEnabled,
                onClick = {
                    onCheckedChange()
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                },
            )
            .padding(3.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(color = contentColor),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
        ) {
            if (isChecked) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_checkbox_check),
                    contentDescription = null,
                    modifier = Modifier.size(width = 10.dp, height = 7.dp),
                )
            }
        }
    }
}

@Composable
fun GoalMateCheckBoxWithText(
    content: String,
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GoalMateCheckBox(
            isChecked = isChecked,
            onCheckedChange = onCheckedChange,
            isEnabled = isEnabled,
        )
        Text(
            text = content,
            style = MaterialTheme.goalMateTypography.body,
            color = MaterialTheme.goalMateColors.onBackground,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun GoalMateCheckButtonPreview() {
    var checked by remember { mutableStateOf(true) }
    GoalMateTheme {
//        GoalMateCheckBox(
//            isChecked = checked,
//            onCheckedChange = { checked = !checked },
//        )
        GoalMateCheckBoxWithText(
            content = "잠자기",
            isChecked = checked,
            onCheckedChange = { checked = !checked },
        )
    }
}

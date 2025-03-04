package cmc.goalmate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.goalMateColors

@Composable
fun GoalMateCheckBox(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
) {
    val contentColor =
        if (isChecked) {
            MaterialTheme.goalMateColors.primary
        } else {
            MaterialTheme.goalMateColors.checkboxBackground
        }

    Box(
        modifier = modifier
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
                    modifier = Modifier,
                )
            }
        }
    }
}

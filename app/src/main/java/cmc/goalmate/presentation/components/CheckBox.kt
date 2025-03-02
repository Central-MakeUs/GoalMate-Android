package cmc.goalmate.presentation.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.util.noRippleClickable

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

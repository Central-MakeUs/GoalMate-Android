package cmc.goalmate.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

enum class ButtonSize {
    LARGE,
    MEDIUM,
    SMALL,
}

@Composable
fun GoalMateButton(
    content: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonSize: ButtonSize = ButtonSize.LARGE,
    hasOutLine: Boolean = false,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(30.dp),
        colors = getButtonColors(hasOutLine),
        contentPadding = getButtonPadding(buttonSize),
        border = if (hasOutLine) {
            BorderStroke(
                width = 2.dp,
                color = MaterialTheme.goalMateColors.disabled,
            )
        } else {
            null
        },
    ) {
        Text(
            text = content,
            style = MaterialTheme.goalMateTypography.buttonLabelLarge,
        )
    }
}

@Composable
fun getButtonPadding(buttonSize: ButtonSize): PaddingValues =
    when (buttonSize) {
        ButtonSize.LARGE -> PaddingValues(horizontal = 56.dp, vertical = 17.5.dp)
        ButtonSize.MEDIUM -> PaddingValues(horizontal = 56.dp, vertical = 14.5.dp)
        ButtonSize.SMALL -> PaddingValues(horizontal = 32.dp, vertical = 12.5.dp)
    }

@Composable
fun getButtonColors(hasOutLine: Boolean): ButtonColors {
    val containerColor =
        if (hasOutLine) MaterialTheme.goalMateColors.background else MaterialTheme.goalMateColors.primary
    val contentColor =
        if (hasOutLine) MaterialTheme.goalMateColors.onBackground else MaterialTheme.goalMateColors.onPrimary

    return ButtonDefaults.buttonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = MaterialTheme.goalMateColors.disabled,
        disabledContentColor = MaterialTheme.goalMateColors.onDisabled,
    )
}

@Composable
fun MoreOptionButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.goalMateTypography.bodySmall,
            color = MaterialTheme.goalMateColors.textButton,
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_forward),
            contentDescription = label,
        )
    }
}

@Composable
@Preview
private fun GoalMateButtonPreview() {
    GoalMateTheme {
        Column {
//            GoalMateButton(
//                content = "버튼",
//                onClick = {},
//                buttonSize = ButtonSize.LARGE,
//            )
            MoreOptionButton(label = "전체 보기", onClick = {})
        }
    }
}

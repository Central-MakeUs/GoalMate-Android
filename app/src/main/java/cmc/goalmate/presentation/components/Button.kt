package cmc.goalmate.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun GoalMateFilledButton(
    content: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.goalMateColors.primary,
            contentColor = MaterialTheme.goalMateColors.onPrimary,
            disabledContainerColor = MaterialTheme.goalMateColors.disabled,
            disabledContentColor = MaterialTheme.goalMateColors.onDisabled,
        ),
    ) {
        Text(
            text = content,
            style = MaterialTheme.goalMateTypography.buttonLabelLarge,
        )
    }
}

@Composable
fun GoalMateOutLinedButton(
    content: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.goalMateColors.background,
            contentColor = MaterialTheme.goalMateColors.onBackground,
            disabledContainerColor = MaterialTheme.goalMateColors.disabled,
            disabledContentColor = MaterialTheme.goalMateColors.onDisabled,
        ),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.goalMateColors.disabled),
    ) {
        Text(
            text = content,
            style = MaterialTheme.goalMateTypography.buttonLabelLarge,
        )
    }
}

@Composable
@Preview
fun GoalMateButtonPreview() {
    GoalMateTheme {
        Column {
            GoalMateFilledButton(
                content = "버튼",
                onClick = {},
            )
            GoalMateOutLinedButton(
                content = "버튼",
                onClick = {},
            )
        }
    }
}

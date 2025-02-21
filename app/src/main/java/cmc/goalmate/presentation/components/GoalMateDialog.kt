package cmc.goalmate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun GoalMateDialog(
    contentText: String,
    buttonText: String,
    onConfirmation: () -> Unit,
) {
    Dialog(
        onDismissRequest = {
            onConfirmation()
        },
    ) {
        Card(
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier.size(320.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.goalMateColors.background)
                    .padding(vertical = GoalMateDimens.BottomMargin),
            ) {
                Spacer(Modifier.weight(1f))
                Text(
                    text = contentText,
                    style = MaterialTheme.goalMateTypography.buttonLabelLarge,
                    color = MaterialTheme.goalMateColors.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.weight(1f))
                GoalMateButton(
                    content = buttonText,
                    onClick = {
                        onConfirmation()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )
            }
        }
    }
}

@Composable
@Preview
private fun GoalMateDialogPreview() {
    GoalMateTheme {
        GoalMateDialog(
            contentText = stringResource(R.string.goal_in_progress_uneditable_warning_message),
            buttonText = "오늘 목표 완료 하기",
            onConfirmation = {},
        )
    }
}

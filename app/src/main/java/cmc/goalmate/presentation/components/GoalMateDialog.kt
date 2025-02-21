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
import androidx.compose.ui.Alignment
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
    buttonText: String,
    onConfirmation: () -> Unit,
    content: @Composable (Modifier) -> Unit,
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
                content(Modifier.fillMaxWidth())
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
fun GoalMateIconDialog(
    subTitle: String,
    contentText: String,
    buttonText: String,
    onConfirmation: () -> Unit,
) {
    GoalMateDialog(
        buttonText = buttonText,
        onConfirmation = { onConfirmation() },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GoalMateImage(
                image = R.drawable.icon_warn,
                modifier = it.size(24.dp)
            )
            Spacer(Modifier.size(20.dp))
            Text(
                text = subTitle,
                style = MaterialTheme.goalMateTypography.subtitleMedium,
                color = MaterialTheme.goalMateColors.onSurface,
            )
            Spacer(Modifier.size(10.dp))
            Text(
                text = contentText,
                style = MaterialTheme.goalMateTypography.tagSmall,
                color = MaterialTheme.goalMateColors.onBackground,
                textAlign = TextAlign.Center
            )

        }
    }
}

@Composable
@Preview
private fun GoalMateDialogPreview() {
    GoalMateTheme {
        GoalMateIconDialog(
            subTitle = "하루 한 번!",
            contentText = "하루 1회만 코멘트를 입력할 수 있어요.\n" + "오늘 보낸 코멘트를 수정해주세요",
            buttonText = "확인했어요",
            onConfirmation = {},
            )
//        GoalMateDialog(
//            buttonText = "오늘 목표 완료 하기",
//            onConfirmation = {},
//        ) {
//            Text(
//                text = stringResource(R.string.goal_in_progress_uneditable_warning_message),
//                style = MaterialTheme.goalMateTypography.buttonLabelLarge,
//                color = MaterialTheme.goalMateColors.onSurfaceVariant,
//                textAlign = TextAlign.Center,
//                modifier = it,
//            )
//        }
    }
}

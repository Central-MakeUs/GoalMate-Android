package cmc.goalmate.presentation.ui.comments.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.Cancel
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.ui.comments.detail.CommentAction
import cmc.goalmate.presentation.ui.util.noRippleClickable

@Composable
fun CommentTextField(
    commentText: String,
    onAction: (CommentAction) -> Unit,
    enabled: Boolean,
    isButtonEnabled: Boolean,
    modifier: Modifier = Modifier,
    showCancelButton: Boolean = false,
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = modifier,
        ) {
            MessageTextField(
                value = commentText,
                onValueChange = { onAction(CommentAction.WriteComment(it)) },
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.size(6.dp))
            if (showCancelButton) {
                CancelButton(onClick = { onAction(CommentAction.CancelEdit) })
            }
            Spacer(Modifier.size(6.dp))
            SubmitButton(
                onClick = { onAction(CommentAction.SendComment(commentText)) },
                enabled = commentText.isNotBlank() && isButtonEnabled,
            )
        }

        if (!enabled) {
            Box(
                modifier = modifier
                    .height(GoalMateDimens.CommentSubmitButtonSize)
                    .background(color = Color.Transparent)
                    .noRippleClickable { onAction(CommentAction.InValidRequest) },
            )
        }
    }
}

@Composable
private fun CancelButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor = if (isPressed) MaterialTheme.goalMateColors.surface else Cancel
    val iconColor =
        if (isPressed) MaterialTheme.goalMateColors.onSurfaceVariant else MaterialTheme.goalMateColors.onBackground

    IconButton(
        onClick = onClick,
        interactionSource = interactionSource,
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = CircleShape,
            )
            .size(GoalMateDimens.CommentSubmitButtonSize)
            .indication(interactionSource, LocalIndication.current),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.icon_cancel_rounded),
            contentDescription = null,
            tint = iconColor,
        )
    }
}

@Composable
private fun SubmitButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (enabled) {
        MaterialTheme.goalMateColors.primary
    } else {
        MaterialTheme.goalMateColors.surface
    }

    val iconTint = if (enabled) {
        MaterialTheme.goalMateColors.onPrimary
    } else {
        MaterialTheme.goalMateColors.background
    }

    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .clip(shape = CircleShape)
            .background(
                color = backgroundColor,
                shape = CircleShape,
            )
            .size(GoalMateDimens.CommentSubmitButtonSize),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.icon_upload),
            contentDescription = null,
            tint = iconTint,
        )
    }
}

@Composable
@Preview
private fun CommentTextFieldPreview() {
    GoalMateTheme {
        CommentTextField(
            commentText = "안녕하세요 안녕하세요",
            onAction = {},
            enabled = false,
            isButtonEnabled = true,
            showCancelButton = true,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

package cmc.goalmate.presentation.ui.comments.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import cmc.goalmate.presentation.ui.comments.detail.CommentAction

@Composable
fun CommentTextField(
    commentText: String,
    onAction: (CommentAction) -> Unit,
    isButtonEnabled: Boolean,
    modifier: Modifier = Modifier,
    showCancelButton: Boolean = false,
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
}

@Composable
private fun CancelButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .background(
                color = MaterialTheme.goalMateColors.surface,
                shape = CircleShape,
            )
            .size(44.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.icon_cancel),
            contentDescription = null,
            tint = MaterialTheme.goalMateColors.onSurfaceVariant,
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
            .background(
                color = backgroundColor,
                shape = CircleShape,
            )
            .size(44.dp),
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
            commentText = "안녕하세요!!",
            onAction = {},
            isButtonEnabled = true,
            showCancelButton = true,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

package cmc.goalmate.presentation.ui.comments.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.goalMateColors

@Composable
fun CommentTextField(
    commentText: String,
    onCommentTextChanged: (String) -> Unit,
    onCancelButtonClicked: () -> Unit,
    onSubmitButtonClicked: () -> Unit,
    showCancelButton: Boolean,
    isCommentTextFieldEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        MessageTextField(
            value = commentText,
            onValueChange = onCommentTextChanged,
            modifier = Modifier.weight(1f),
        )
        Spacer(Modifier.size(6.dp))
        if (showCancelButton) {
            CancelButton(onClick = onCancelButtonClicked)
        }
        Spacer(Modifier.size(6.dp))
        SubmitButton(
            onClick = onSubmitButtonClicked,
            enabled = commentText.isNotBlank() && isCommentTextFieldEnabled,
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

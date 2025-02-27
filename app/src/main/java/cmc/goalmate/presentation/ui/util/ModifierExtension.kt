package cmc.goalmate.presentation.ui.util

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier =
    composed {
        clickable(
            onClick = onClick,
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
        )
    }

fun Modifier.singleClickable(
    noRipple: Boolean = false,
    onClick: () -> Unit,
) = composed {
    singleClickEvent { manager ->
        clickable(
            onClick = { manager.event { onClick() } },
            indication = if (noRipple) null else LocalIndication.current,
            interactionSource = remember { MutableInteractionSource() },
        )
    }
}

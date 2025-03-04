package cmc.goalmate.presentation.ui.util

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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
    enabled: Boolean = true,
    onClick: () -> Unit,
) = composed {
    singleClickEvent { manager ->
        clickable(
            onClick = { manager.event { onClick() } },
            indication = if (noRipple) null else LocalIndication.current,
            interactionSource = remember { MutableInteractionSource() },
            enabled = enabled,
        )
    }
}

fun Modifier.crop(
    horizontal: Dp = 0.dp,
    vertical: Dp = 0.dp,
): Modifier =
    this.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        fun Dp.toPxInt(): Int = this.toPx().toInt()

        layout(
            placeable.width - (horizontal * 2).toPxInt(),
            placeable.height - (vertical * 2).toPxInt(),
        ) {
            placeable.placeRelative(-horizontal.toPx().toInt(), -vertical.toPx().toInt())
        }
    }

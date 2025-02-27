package cmc.goalmate.presentation.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce

interface SingleClickEventInterface {
    fun event(event: () -> Unit)
}

@OptIn(FlowPreview::class)
@Composable
fun <T> singleClickEvent(content: @Composable (SingleClickEventInterface) -> T): T {
    val debounceState = remember {
        MutableSharedFlow<() -> Unit>(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )
    }

    val result = content(
        object : SingleClickEventInterface {
            override fun event(event: () -> Unit) {
                debounceState.tryEmit(event)
            }
        },
    )

    LaunchedEffect(true) {
        debounceState
            .debounce(200L)
            .collect { onClick ->
                onClick.invoke()
            }
    }

    return result
}

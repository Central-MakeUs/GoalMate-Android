package cmc.goalmate.presentation.ui.progress.inprogress.model

import kotlinx.coroutines.flow.StateFlow

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>

    data class Error(val message: String) : UiState<Nothing>

    data class Success<T>(val data: T) : UiState<T>
}

fun <T> StateFlow<UiState<T>>.successData(): T = (this.value as UiState.Success).data

fun <T> UiState<T>.successData(): T = (this as UiState.Success).data

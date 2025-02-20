package cmc.goalmate.presentation.ui.progress.inprogress.model

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>

    data class Error(val message: String) : UiState<Nothing>

    data class Success<T>(val data: T) : UiState<T>
}

fun <T> UiState<T>.getSuccessDataOrNull(): T? = (this as? UiState.Success)?.data

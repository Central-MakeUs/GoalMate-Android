package cmc.goalmate.presentation.ui.common

sealed class UserState<out T> {
    data class LoggedIn<out T>(val data: T) : UserState<T>()

    data object LoggedOut : UserState<Nothing>()
}

fun <T> UserState<T>.getDataOrNull(): T? =
    when (this) {
        is UserState.LoggedIn -> this.data
        is UserState.LoggedOut -> null
    }

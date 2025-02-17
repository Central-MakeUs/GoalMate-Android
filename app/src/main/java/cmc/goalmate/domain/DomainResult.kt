package cmc.goalmate.domain

typealias RootError = Error

sealed interface DomainResult<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : DomainResult<D, E>

    data class Error<out D, out E : RootError>(val error: E) : DomainResult<D, E>
}

inline fun <D, E : RootError> DomainResult<D, E>.onSuccess(block: (D) -> Unit): DomainResult<D, E> {
    if (this is DomainResult.Success) {
        block(data)
    }
    return this
}

inline fun <D, E : RootError> DomainResult<D, E>.onFailure(block: (E) -> Unit): DomainResult<D, E> {
    if (this is DomainResult.Error) {
        block(error)
    }
    return this
}

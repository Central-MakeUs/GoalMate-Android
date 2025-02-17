package cmc.goalmate.domain

typealias RootError = Error

sealed interface DomainResult<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : DomainResult<D, E>

    data class Error<out D, out E : RootError>(val error: E) : DomainResult<D, E>
}

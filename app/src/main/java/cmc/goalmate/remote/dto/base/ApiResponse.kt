package cmc.goalmate.remote.dto.base

sealed interface ApiResponse<out T> {
    data class Success<T : Any>(val data: T) : ApiResponse<T>

    sealed class Failure(val error: Throwable) : ApiResponse<Nothing> {
        data class HttpException(val code: Int, val throwable: Throwable) : Failure(throwable)

        data class NetworkException(val throwable: Throwable) : Failure(throwable)

        data class UnknownError(val throwable: Throwable) : Failure(throwable)
    }
}

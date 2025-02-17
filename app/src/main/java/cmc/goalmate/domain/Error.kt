package cmc.goalmate.domain

sealed interface Error

sealed interface DataError : Error {
    enum class Network : DataError {
        NO_INTERNET,
        SERVER_ERROR,
        NOT_FOUND,
        UNAUTHORIZED,
        CONFLICT,
        UNKNOWN,
    }
}

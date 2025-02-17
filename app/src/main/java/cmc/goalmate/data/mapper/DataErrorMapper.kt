package cmc.goalmate.data.mapper

import cmc.goalmate.data.exception.HttpException
import cmc.goalmate.data.exception.NetworkException
import cmc.goalmate.data.exception.UnknownException
import cmc.goalmate.domain.DataError

fun Throwable.toDataError(): DataError.Network =
    when (this) {
        is HttpException -> {
            when (this.code) {
                401 -> DataError.Network.UNAUTHORIZED
                404 -> DataError.Network.NOT_FOUND
                409 -> DataError.Network.CONFLICT
                in 500..599 -> DataError.Network.SERVER_ERROR
                else -> DataError.Network.UNKNOWN
            }
        }
        is NetworkException -> DataError.Network.NO_INTERNET
        is UnknownException -> DataError.Network.UNKNOWN
        else -> DataError.Network.UNKNOWN
    }

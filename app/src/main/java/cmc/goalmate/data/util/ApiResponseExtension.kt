package cmc.goalmate.data.util

import cmc.goalmate.data.exception.HttpException
import cmc.goalmate.data.exception.NetworkException
import cmc.goalmate.data.exception.UnknownException
import cmc.goalmate.remote.dto.base.ApiResponse
import cmc.goalmate.remote.dto.response.BaseResponse

fun <T> ApiResponse<BaseResponse<T>>.getOrThrow(): T =
    when (this) {
        is ApiResponse.Success -> data.data
        is ApiResponse.Failure -> {
            throw when (this) {
                is ApiResponse.Failure.HttpException ->
                    HttpException(code = this.code, throwable = this.throwable)
                is ApiResponse.Failure.NetworkException ->
                    NetworkException(throwable = this.throwable)
                is ApiResponse.Failure.UnknownError ->
                    UnknownException(throwable = this.throwable)
            }
        }
    }

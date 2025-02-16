package cmc.goalmate.remote.adapter

import cmc.goalmate.remote.dto.base.ApiResponse
import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoalMateCall<T>(
    private val call: Call<T>,
) : Call<ApiResponse<T>> {
    override fun enqueue(callback: Callback<ApiResponse<T>>) {
        call.enqueue(
            object : Callback<T> {
                override fun onResponse(
                    call: Call<T>,
                    response: Response<T>,
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        val successResponse = if (body != null) {
                            ApiResponse.Success(body)
                        } else {
                            ApiResponse.Failure.UnknownError(throwable = IllegalStateException("body is null"))
                        }
                        callback.onResponse(this@GoalMateCall, Response.success(successResponse))
                        return
                    }
                    val errorResponse = ApiResponse.Failure.HttpException(
                        code = response.code(),
                        throwable = IOException(response.errorBody()?.string()),
                    )
                    callback.onResponse(this@GoalMateCall, Response.success(errorResponse))
                }

                override fun onFailure(
                    call: Call<T>,
                    throwable: Throwable,
                ) {
                    val errorResponse = when (throwable) {
                        is IOException -> ApiResponse.Failure.NetworkException(throwable.message ?: "IO exception occurred")
                        else -> ApiResponse.Failure.UnknownError(throwable)
                    }
                    callback.onResponse(this@GoalMateCall, Response.success(errorResponse))
                }
            },
        )
    }

    override fun clone(): Call<ApiResponse<T>> = GoalMateCall(call.clone())

    override fun execute(): Response<ApiResponse<T>> = throw UnsupportedOperationException("GoalMateCall doesn't support execute")

    override fun isExecuted(): Boolean = call.isExecuted

    override fun cancel() = call.cancel()

    override fun isCanceled(): Boolean = call.isCanceled

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()
}

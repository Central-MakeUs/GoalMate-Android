package cmc.goalmate.remote.adapter

import cmc.goalmate.remote.dto.base.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class GoalMateCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        if (Call::class.java != getRawType(returnType)) return null
        require(returnType is ParameterizedType) {
            "return type must be parameterized as Call<ApiResponse<<Foo>>"
        }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != ApiResponse::class.java) return null
        require(responseType is ParameterizedType) {
            "Response must be parameterized as ApiResponse<Foo>"
        }

        val successBodyType = getParameterUpperBound(0, responseType)
        return GoalMateCallAdapter<Any>(successBodyType)
    }
}

private class GoalMateCallAdapter<T>(private val responseType: Type) :
    CallAdapter<T, Call<ApiResponse<T>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Call<ApiResponse<T>> = GoalMateCall(call)
}

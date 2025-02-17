package cmc.goalmate.remote.interceptor

import cmc.goalmate.remote.dto.response.BaseResponse
import cmc.goalmate.remote.dto.response.LoginResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

class LoginInterceptor : Interceptor {
    private val json = Json { ignoreUnknownKeys = true }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (request.url.encodedPath == "/auth/login") {
            val response = chain.proceed(request)
            val responseBody = response.body?.string() ?: return response

            try {
                val baseResponse = json.decodeFromString<BaseResponse<LoginResponse>>(responseBody)
                val requiresNickname = response.code == 201

                val updatedBaseResponse = baseResponse.copy(
                    data = baseResponse.data.copy(requiresNickname = requiresNickname),
                )
                val modifiedResponseBody = json.encodeToString(updatedBaseResponse)
                val newResponseBody: ResponseBody =
                    modifiedResponseBody.toResponseBody(response.body?.contentType())

                return response.newBuilder()
                    .body(newResponseBody)
                    .build()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return chain.proceed(request)
    }
}

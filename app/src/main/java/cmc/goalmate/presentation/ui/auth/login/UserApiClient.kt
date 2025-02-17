package cmc.goalmate.presentation.ui.auth.login

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun UserApiClient.Companion.loginWithKakao(context: Context): OAuthToken =
    if (instance.isKakaoTalkLoginAvailable(context)) {
        try {
            UserApiClient.loginWithKakaoTalk(context)
        } catch (error: Throwable) {
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) throw error
            UserApiClient.loginWithKakaoAccount(context)
        }
    } else {
        UserApiClient.loginWithKakaoAccount(context)
    }

suspend fun UserApiClient.Companion.loginWithKakaoTalk(context: Context): OAuthToken =
    suspendCoroutine<OAuthToken> { continuation ->
        instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                continuation.resumeWithException(error)
            } else if (token != null) {
                continuation.resume(token)
            } else {
                continuation.resumeWithException(RuntimeException("kakao access token을 받아오는데 실패함, 이유는 명확하지 않음."))
            }
        }
    }

suspend fun UserApiClient.Companion.loginWithKakaoAccount(context: Context): OAuthToken =
    suspendCoroutine<OAuthToken> { continuation ->
        instance.loginWithKakaoAccount(context) { token, error ->
            if (error != null) {
                continuation.resumeWithException(error)
            } else if (token != null) {
                continuation.resume(token)
            } else {
                continuation.resumeWithException(RuntimeException("kakao access token을 받아오는데 실패함, 이유는 명확하지 않음."))
            }
        }
    }

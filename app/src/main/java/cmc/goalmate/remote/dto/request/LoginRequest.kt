package cmc.goalmate.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("identity_token") val identityToken: String,
    val nonce: String = System.currentTimeMillis().toString(),
    val provider: String = "KAKAO",
)

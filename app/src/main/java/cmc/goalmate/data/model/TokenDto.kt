package cmc.goalmate.data.model

import cmc.goalmate.domain.model.Token
import cmc.goalmate.remote.dto.response.TokenResponse

data class TokenDto(val accessToken: String, val refreshToken: String)

fun TokenDto.toDomain(): Token = Token(accessToken, refreshToken)

fun TokenResponse.toData(): TokenDto = TokenDto(accessToken, refreshToken)

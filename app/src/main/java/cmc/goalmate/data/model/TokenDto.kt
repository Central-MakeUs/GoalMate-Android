package cmc.goalmate.data.model

import cmc.goalmate.domain.model.Token

data class TokenDto(val accessToken: String, val refreshToken: String)

fun TokenDto.toDomain(): Token = Token(accessToken, refreshToken)

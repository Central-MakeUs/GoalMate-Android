package cmc.goalmate.domain.model

import cmc.goalmate.data.model.TokenDto

data class Token(val accessToken: String, val refreshToken: String)

fun Token.toData(): TokenDto = TokenDto(accessToken, refreshToken)

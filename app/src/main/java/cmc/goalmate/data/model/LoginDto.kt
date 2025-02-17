package cmc.goalmate.data.model

import cmc.goalmate.domain.model.Login
import cmc.goalmate.remote.dto.response.LoginResponse

data class LoginDto(val requiresNickname: Boolean, val token: TokenDto)

fun LoginResponse.toData(): LoginDto = LoginDto(requiresNickname = requiresNickname, token = TokenDto(accessToken, refreshToken))

fun LoginDto.toDomain(): Login = Login(isRegistered = !requiresNickname, token = token.toDomain())

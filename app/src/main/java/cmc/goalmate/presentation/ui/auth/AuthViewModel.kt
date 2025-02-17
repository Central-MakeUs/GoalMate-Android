package cmc.goalmate.presentation.ui.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.DataError
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.ValidateNickName
import cmc.goalmate.domain.repository.AuthRepository
import cmc.goalmate.presentation.components.InputTextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val validateNickName: ValidateNickName,
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        private val _state = MutableStateFlow<AuthUiState>(AuthUiState.initialLoginUiState())
        val state: StateFlow<AuthUiState>
            get() = _state

        var nickName by mutableStateOf("")
            private set

        fun onAction(action: AuthAction) {
            when (action) {
                is AuthAction.KakaoLogin -> loginWithKakao(action.idToken)
                is AuthAction.SetNickName -> updateNickName(action.nickName)
                AuthAction.CheckDuplication -> checkNickNameDuplication()
                AuthAction.CompleteNicknameSetup -> Unit
            }
        }

        private fun loginWithKakao(idToken: String?) {
            requireNotNull(idToken) { "idToken 이 null" }
            viewModelScope.launch {
                when (val result = authRepository.login(idToken)) {
                    is DomainResult.Error -> {
                        val errorMessage = result.error.asUiText()
                        Log.d("yenny", "error : $errorMessage")
                    }
                    is DomainResult.Success -> {
                        result.data
                    }
                }
            }
        }

        private fun updateNickName(newNickName: String) {
            nickName = newNickName

            if (nickName.isBlank()) {
                _state.value = _state.value.copy(
                    nickNameFormatValidationState = InputTextState.None,
                    duplicationCheckState = InputTextState.None,
                )
                return
            }

            val result = validateNickName(nickName = newNickName)
            if (result is DomainResult.Success) {
                _state.value = _state.value.copy(
                    nickNameFormatValidationState = InputTextState.Success,
                    helperText = "",
                )
            } else {
                _state.value = _state.value.copy(
                    nickNameFormatValidationState = InputTextState.Error,
                    helperText = "2~5글자 닉네임을 입력해주세요.",
                    duplicationCheckState = InputTextState.None,
                )
            }
        }

        private fun checkNickNameDuplication() {
            // 검사 후, 중복된 닉네임이 없다면,
            _state.value = _state.value.copy(
                nickNameFormatValidationState = InputTextState.Success,
                duplicationCheckState = InputTextState.Success,
                helperText = "사용 가능한 닉네임이에요 :)",
            )
        }
    }

fun DataError.asUiText(): String =
    when (this) {
        DataError.Network.NO_INTERNET -> "No internet connection"
        DataError.Network.SERVER_ERROR -> "Server error"
        DataError.Network.NOT_FOUND -> "Not found"
        DataError.Network.UNAUTHORIZED -> "Unauthorized"
        DataError.Network.CONFLICT -> "Conflict"
        DataError.Network.UNKNOWN -> "Unknown error"
    }

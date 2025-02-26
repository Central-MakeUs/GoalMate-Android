package cmc.goalmate.presentation.ui.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmc.goalmate.domain.DomainResult
import cmc.goalmate.domain.model.Token
import cmc.goalmate.domain.onFailure
import cmc.goalmate.domain.onSuccess
import cmc.goalmate.domain.repository.AuthRepository
import cmc.goalmate.domain.repository.UserRepository
import cmc.goalmate.presentation.components.InputTextState
import cmc.goalmate.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val _state = MutableStateFlow<AuthUiState>(AuthUiState.initialLoginUiState())
        val state: StateFlow<AuthUiState>
            get() = _state

        private val _authEvent = Channel<AuthEvent>()
        val authEvent = _authEvent.receiveAsFlow()

        var nickName by mutableStateOf("")
            private set

        private var tempToken: Token? = null

        fun onAction(action: AuthAction) {
            when (action) {
                is AuthAction.KakaoLogin -> loginWithKakao(action.idToken)
                AuthAction.AgreeTermsOfService -> agreeTermsOfService()
                AuthAction.CheckDuplication -> checkNickNameDuplication()
                is AuthAction.SetNickName -> updateNickName(action.nickName)
                AuthAction.CompleteNicknameSetup -> completeNickNameSetting()
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
                        if (result.data.isRegistered) {
                            authRepository.saveToken(result.data.token)
                                .onSuccess {
                                    _state.value = _state.value.copy(isLoginCompleted = true)
                                    _authEvent.send(AuthEvent.NavigateToHome)
                                }
                        } else {
                            tempToken = result.data.token
                            _authEvent.send(AuthEvent.GetAgreeWithTerms)
                        }
                    }
                }
            }
        }

        private fun agreeTermsOfService() {
            viewModelScope.launch {
                requireNotNull(tempToken) { "저장된 토큰이 없음" }
                authRepository.saveToken(requireNotNull(tempToken) { "저장된 토큰이 없음" })
                    .onSuccess {
                        _authEvent.send(AuthEvent.NavigateToNickNameSetting)
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

            userRepository.checkNicknameValidity(nickName = newNickName)
                .onSuccess {
                    _state.value = _state.value.copy(
                        nickNameFormatValidationState = InputTextState.Success,
                        helperText = "",
                    )
                }
                .onFailure {
                    _state.value = _state.value.copy(
                        nickNameFormatValidationState = InputTextState.Error,
                        helperText = it.asUiText(),
                        duplicationCheckState = InputTextState.None,
                    )
                }
        }

        private fun checkNickNameDuplication() {
            viewModelScope.launch {
                userRepository.checkNicknameAvailable(nickName)
                    .onSuccess {
                        _state.value = _state.value.copy(
                            nickNameFormatValidationState = InputTextState.Success,
                            duplicationCheckState = InputTextState.Success,
                            helperText = "사용 가능한 닉네임이에요 :)",
                        )
                    }
                    .onFailure {
                        _state.value = _state.value.copy(
                            duplicationCheckState = InputTextState.Error,
                            helperText = it.asUiText(),
                        )
                    }
            }
        }

        private fun completeNickNameSetting() {
            viewModelScope.launch {
                userRepository.updateNickName(nickName)
                    .onSuccess {
                        _state.value = _state.value.copy(isLoginCompleted = true)
                        _authEvent.send(AuthEvent.NavigateToCompleted)
                    }
                    .onFailure {
                        Log.d("yenny", "completeNickNameSetting error : ${it.asUiText()}")
                    }
            }
        }

        override fun onCleared() {
            viewModelScope.launch {
                suspendCancellableCoroutine<Unit> { continuation ->
                    if (!state.value.isLoginCompleted) {
                        launch {
                            authRepository.deleteToken()
                                .onSuccess {
                                    Log.d("yenny", "delete token successfully!")
                                    continuation.resume(Unit)
                                }
                        }
                    } else {
                        continuation.resume(Unit)
                    }
                }
            }
            super.onCleared()
        }
    }

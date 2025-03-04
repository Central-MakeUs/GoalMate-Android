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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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

        private fun sendEvent(event: AuthEvent) {
            viewModelScope.launch {
                _authEvent.send(event)
            }
        }

        private fun loginWithKakao(idToken: String?) {
            requireNotNull(idToken) { "idToken 이 null" }
            if (tempToken != null) {
                sendEvent(AuthEvent.GetAgreeWithTerms)
                return
            }

            _state.update { current -> current.copy(isLoading = true) }
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
                                    _state.update { current -> current.copy(isLoading = false) }
                                    _authEvent.send(AuthEvent.NavigateToHome)
                                }
                            return@launch
                        }
                        tempToken = result.data.token
                        _state.update { current -> current.copy(isLoading = false) }
                        sendEvent(AuthEvent.GetAgreeWithTerms)
                    }
                }
            }
        }

        private fun agreeTermsOfService() {
            viewModelScope.launch {
                requireNotNull(tempToken) { "저장된 토큰이 없음" }
                _authEvent.send(AuthEvent.NavigateToNickNameSetting)
            }
        }

        private fun updateNickName(newNickName: String) {
            nickName = newNickName

            if (nickName.isBlank()) {
                _state.value = _state.value.copy(
                    nickNameFormatValidationState = InputTextState.None,
                    duplicationCheckState = InputTextState.None,
                    helperText = "",
                )
                return
            }

            userRepository.checkNicknameValidity(nickName = newNickName)
                .onSuccess {
                    _state.value = _state.value.copy(
                        nickNameFormatValidationState = InputTextState.Success,
                        duplicationCheckState = InputTextState.None,
                        helperText = "",
                    )
                }
                .onFailure {
                    _state.value = _state.value.copy(
                        nickNameFormatValidationState = InputTextState.Error,
                        duplicationCheckState = InputTextState.None,
                        helperText = it.asUiText(),
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
                authRepository.saveToken(requireNotNull(tempToken) { "저장된 토큰이 없음" })
                    .onSuccess {
                        userRepository.updateNickName(nickName)
                            .onSuccess {
                                _authEvent.send(AuthEvent.NavigateToCompleted)
                            }
                            .onFailure {
                                Log.d("yenny", "completeNickNameSetting error : ${it.asUiText()}")
                            }
                    }
            }
        }
    }

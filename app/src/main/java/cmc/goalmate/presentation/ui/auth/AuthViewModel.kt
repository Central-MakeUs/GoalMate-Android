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
import cmc.goalmate.presentation.ui.auth.AuthUiState.Companion.DEFAULT_HELPER_TEXT
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

        fun onAction(action: AuthAction) {
            when (action) {
                is AuthAction.KakaoLogin -> loginWithKakao(action.idToken)
                AuthAction.AgreeTermsOfService -> agreeTermsOfService()
                is AuthAction.SetNickName -> updateNickName(action.nickName)
                AuthAction.CheckDuplication -> checkNickNameDuplication()
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

            _state.update { current -> current.copy(isLoading = true) }
            viewModelScope.launch {
                when (val result = authRepository.login(idToken)) {
                    is DomainResult.Error -> {
                        val errorMessage = result.error.asUiText()
                        Log.d("yenny", "error : $errorMessage")
                    }
                    is DomainResult.Success -> {
                        val token = result.data.token
                        if (result.data.isRegistered) {
                            handleLogin(token)
                            return@launch
                        }
                        handleSignUp(token)
                    }
                }
            }
        }

        private suspend fun handleLogin(token: Token) {
            authRepository.saveToken(token)
                .onSuccess {
                    _state.update { current -> current.copy(isLoading = false) }
                    _authEvent.send(AuthEvent.NavigateToHome)
                }
        }

        private suspend fun handleSignUp(token: Token) {
            authRepository.saveToken(token)
                .onSuccess {
                    userRepository.getUserInfo()
                        .onSuccess { userInfo ->
                            _state.update { current ->
                                current.copy(
                                    isLoading = false,
                                    defaultNickName = userInfo.nickName,
                                )
                            }
                            sendEvent(AuthEvent.GetAgreeWithTerms)
                        }
                }
        }

        private fun agreeTermsOfService() {
            viewModelScope.launch {
                _authEvent.send(AuthEvent.NavigateToNickNameSetting)
            }
        }

        private fun updateNickName(newNickName: String) {
            nickName = newNickName.trim()

            if (nickName.isEmpty()) {
                _state.update { current ->
                    current.copy(
                        nickNameFormatValidationState = InputTextState.None,
                        duplicationCheckState = InputTextState.None,
                        helperText = DEFAULT_HELPER_TEXT,
                    )
                }
            }

            userRepository.checkNicknameValidity(nickName = nickName)
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
            if (nickName.isEmpty()) {
                sendEvent(AuthEvent.NavigateToCompleted(confirmedNickName = state.value.defaultNickName))
                return
            }
            viewModelScope.launch {
                userRepository.updateNickName(nickName)
                    .onSuccess {
                        sendEvent(AuthEvent.NavigateToCompleted(confirmedNickName = nickName))
                    }
                    .onFailure {
                        Log.d("yenny", "completeNickNameSetting error : ${it.asUiText()}")
                    }
            }
        }
    }

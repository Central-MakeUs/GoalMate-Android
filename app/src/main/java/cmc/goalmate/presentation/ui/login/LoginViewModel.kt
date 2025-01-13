package cmc.goalmate.presentation.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cmc.goalmate.presentation.components.InputTextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class LoginUiState(
    val loginSteps: List<Step>,
    val nickNameValidationState: InputTextState,
    val helperText: String,
    val isDuplicationCheckEnabled: Boolean,
) {
    val isNextStepEnabled: Boolean
        get() = nickNameValidationState == InputTextState.Success

    companion object {
        fun initialLoginUiState(): LoginUiState =
            LoginUiState(
                loginSteps = createInitialLoginSteps(),
                nickNameValidationState = InputTextState.None,
                helperText = "",
                isDuplicationCheckEnabled = false,
            )
    }
}

sealed interface LoginAction {
    data object KakaoLogin : LoginAction

    data class SetNickName(val nickName: String) : LoginAction

    data object CheckDuplication : LoginAction

    data object CompleteNicknameSetup : LoginAction
}

@HiltViewModel
class LoginViewModel
    @Inject
    constructor() : ViewModel() {
        private val _state = MutableStateFlow<LoginUiState>(LoginUiState.initialLoginUiState())
        val state: StateFlow<LoginUiState>
            get() = _state

        var nickName by mutableStateOf("")
            private set

        fun onAction(action: LoginAction) {
            when (action) {
                LoginAction.KakaoLogin -> loginWithKakao()
                is LoginAction.SetNickName -> updateNickName(action.nickName)
                LoginAction.CheckDuplication -> checkNickNameDuplication()
                LoginAction.CompleteNicknameSetup -> Unit
            }
        }

        private fun loginWithKakao() {
        }

        private fun updateNickName(newNickName: String) {
            nickName = newNickName
            // validation 검사
            _state.value = _state.value.copy(isDuplicationCheckEnabled = true)
        }

        private fun checkNickNameDuplication() {
            // 검사 후, 중복된 닉네임이 없다면,
            _state.value = _state.value.copy(
                nickNameValidationState = InputTextState.Success,
                isDuplicationCheckEnabled = false,
            )
        }
    }

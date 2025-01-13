package cmc.goalmate.presentation.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cmc.goalmate.domain.ValidateNickName
import cmc.goalmate.presentation.components.InputTextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class LoginUiState(
    val loginSteps: List<Step>,
    val nickNameFormatValidationState: InputTextState,
    val duplicationCheckState: InputTextState,
    val helperText: String,
) {
    val isDuplicationCheckEnabled: Boolean
        get() = nickNameFormatValidationState == InputTextState.Success

    val isNextStepEnabled: Boolean
        get() = (nickNameFormatValidationState == InputTextState.Success) && (duplicationCheckState == InputTextState.Success)

    val validationState: InputTextState
        get() = when {
            nickNameFormatValidationState == InputTextState.Success && duplicationCheckState == InputTextState.Success -> InputTextState.Success
            nickNameFormatValidationState == InputTextState.Error || duplicationCheckState == InputTextState.Error -> InputTextState.Error
            else -> InputTextState.None
        }

    companion object {
        fun initialLoginUiState(): LoginUiState =
            LoginUiState(
                loginSteps = createInitialLoginSteps(),
                nickNameFormatValidationState = InputTextState.None,
                duplicationCheckState = InputTextState.None,
                helperText = "",
            )
    }
}

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val validateNickName: ValidateNickName,
    ) : ViewModel() {
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

            if (nickName.isBlank()) {
                _state.value = _state.value.copy(
                    nickNameFormatValidationState = InputTextState.None,
                    duplicationCheckState = InputTextState.None,
                )
                return
            }

            val result = validateNickName(nickName = newNickName)
            if (result.successful) {
                _state.value = _state.value.copy(
                    nickNameFormatValidationState = InputTextState.Success,
                )
            } else {
                _state.value = _state.value.copy(
                    nickNameFormatValidationState = InputTextState.Error,
                )
            }
            _state.value = _state.value.copy(
                helperText = result.errorMessage ?: "",
                duplicationCheckState = InputTextState.None,
            )
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

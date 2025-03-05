package cmc.goalmate.presentation.ui.auth

import cmc.goalmate.presentation.components.InputTextState

data class AuthUiState(
    val isLoading: Boolean,
    val isLoginCompleted: Boolean,
    val nickNameFormatValidationState: InputTextState,
    val duplicationCheckState: InputTextState,
    val helperText: String,
    val defaultNickName: String,
) {
    val isDuplicationCheckEnabled: Boolean
        get() = nickNameFormatValidationState == InputTextState.Success && duplicationCheckState != InputTextState.Success

    val validationState: InputTextState
        get() = when {
            nickNameFormatValidationState == InputTextState.Success &&
                duplicationCheckState == InputTextState.Success -> InputTextState.Success
            nickNameFormatValidationState == InputTextState.Error ||
                duplicationCheckState == InputTextState.Error -> InputTextState.Error
            else -> InputTextState.None
        }

    fun isNextStepButtonEnabled(nickNameText: String): Boolean =
        when {
            nickNameText.isEmpty() -> true
            nickNameFormatValidationState != InputTextState.Success -> false
            duplicationCheckState != InputTextState.Success -> false
            else -> true
        }

    companion object {
        fun initialLoginUiState(): AuthUiState =
            AuthUiState(
                isLoading = false,
                isLoginCompleted = false,
                nickNameFormatValidationState = InputTextState.Empty,
                duplicationCheckState = InputTextState.None,
                helperText = DEFAULT_HELPER_TEXT,
                defaultNickName = "",
            )

        const val DEFAULT_HELPER_TEXT = "2~5 글자 닉네임을 입력해주세요"
    }
}

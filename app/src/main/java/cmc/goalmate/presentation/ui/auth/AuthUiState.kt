package cmc.goalmate.presentation.ui.auth

import cmc.goalmate.presentation.components.InputTextState

data class AuthUiState(
    val isLoading: Boolean,
    val isLoginCompleted: Boolean,
    val nickNameFormatValidationState: InputTextState,
    val duplicationCheckState: InputTextState,
    val helperText: String,
) {
    val isDuplicationCheckEnabled: Boolean
        get() = nickNameFormatValidationState == InputTextState.Success && duplicationCheckState != InputTextState.Success

    val isNextStepEnabled: Boolean
        get() = (nickNameFormatValidationState == InputTextState.Success) && (duplicationCheckState == InputTextState.Success)

    val validationState: InputTextState
        get() = when {
            nickNameFormatValidationState == InputTextState.Success && duplicationCheckState == InputTextState.Success -> InputTextState.Success
            nickNameFormatValidationState == InputTextState.Error || duplicationCheckState == InputTextState.Error -> InputTextState.Error
            else -> InputTextState.None
        }

    companion object {
        fun initialLoginUiState(): AuthUiState =
            AuthUiState(
                isLoading = false,
                isLoginCompleted = false,
                nickNameFormatValidationState = InputTextState.None,
                duplicationCheckState = InputTextState.None,
                helperText = "",
            )
    }
}

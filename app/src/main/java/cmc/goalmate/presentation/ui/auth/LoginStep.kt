package cmc.goalmate.presentation.ui.auth

import cmc.goalmate.presentation.ui.auth.component.Step

fun createInitialLoginSteps(): List<Step> =
    LoginStep.entries.mapIndexed { index, step ->
        Step(
            step = step,
            status = if (index == 0) StepStatus.CURRENT else StepStatus.PENDING,
        )
    }

val firstStep = listOf(
    Step(LoginStep.SIGN_UP, StepStatus.CURRENT),
    Step(LoginStep.NICKNAME_SETTING, StepStatus.PENDING),
    Step(LoginStep.COMPLETED, StepStatus.PENDING),
)

val secondStep = listOf(
    Step(LoginStep.SIGN_UP, StepStatus.COMPLETED),
    Step(LoginStep.NICKNAME_SETTING, StepStatus.CURRENT),
    Step(LoginStep.COMPLETED, StepStatus.PENDING),
)

val lastStep = listOf(
    Step(LoginStep.SIGN_UP, StepStatus.COMPLETED),
    Step(LoginStep.NICKNAME_SETTING, StepStatus.COMPLETED),
    Step(LoginStep.COMPLETED, StepStatus.CURRENT),
)

enum class LoginStep(val step: String, val title: String) {
    SIGN_UP("1", "회원가입"),
    NICKNAME_SETTING("2", "닉네임 설정"),
    COMPLETED("3", "시작하기"),
    ;

    fun isFirstStep(): Boolean = this == SIGN_UP

    fun isLastStep(): Boolean = this == COMPLETED
}

enum class StepStatus {
    CURRENT,
    PENDING,
    COMPLETED,
    ;

    fun previous(): StepStatus =
        when (this) {
            CURRENT -> COMPLETED
            PENDING -> PENDING
            COMPLETED -> COMPLETED
        }
}

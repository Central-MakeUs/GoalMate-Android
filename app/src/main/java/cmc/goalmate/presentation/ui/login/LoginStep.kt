package cmc.goalmate.presentation.ui.login

fun createInitialLoginSteps(): List<Step> =
    LoginStep.entries.mapIndexed { index, step ->
        Step(
            step = step,
            status = if (index == 0) StepStatus.CURRENT else StepStatus.PENDING,
        )
    }

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

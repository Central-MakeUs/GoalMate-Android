package cmc.goalmate.presentation.ui.auth.login.model

import androidx.annotation.StringRes
import cmc.goalmate.R

enum class TermOption(
    @StringRes val contentResId: Int,
    val termUrl: String?,
) {
    OPTION_1(
        R.string.login_term_of_service_option_1,
        "https://ash-oregano-9dc.notion.site/f97185c23c5444b4ae3796928ae7f646?pvs=4",
    ),
    OPTION_2(
        R.string.login_term_of_service_option_2,
        "https://www.notion.so/997827990f694f63a60b06c06beb1468?pvs=4",
    ),
    OPTION_3(R.string.login_term_of_service_option_3, null),
}

data class TermOptionState(
    val termOption: TermOption,
    val isChecked: Boolean = false,
) {
    companion object {
        val DEFAULT = listOf(
            TermOptionState(TermOption.OPTION_1),
            TermOptionState(TermOption.OPTION_2),
            TermOptionState(TermOption.OPTION_3),
        )

        fun List<TermOptionState>.isAllChecked(): Boolean = this.all { it.isChecked }
    }
}

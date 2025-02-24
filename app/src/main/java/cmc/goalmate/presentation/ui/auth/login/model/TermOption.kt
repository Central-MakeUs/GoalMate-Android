package cmc.goalmate.presentation.ui.auth.login.model

import androidx.annotation.StringRes
import cmc.goalmate.R
import cmc.goalmate.presentation.ui.common.WebScreenUrl

enum class TermOption(
    @StringRes val contentResId: Int,
    val termUrl: WebScreenUrl?,
) {
    OPTION_1(
        R.string.login_term_of_service_option_1,
        WebScreenUrl.TermsOfService,
    ),
    OPTION_2(
        R.string.login_term_of_service_option_2,
        WebScreenUrl.PrivacyPolicy,
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

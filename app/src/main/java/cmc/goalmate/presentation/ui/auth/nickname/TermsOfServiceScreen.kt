package cmc.goalmate.presentation.ui.auth.nickname

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.ButtonSize
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.components.GoalMateCheckBox
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateTypography

data class TermOption(
    @StringRes val contentResId: Int,
    val isChecked: Boolean = false,
) {
    companion object {
        val DEFAULT = listOf(
            TermOption(
                contentResId = R.string.login_term_of_service_option_1,
            ),
            TermOption(
                contentResId = R.string.login_term_of_service_option_2,
            ),
            TermOption(
                contentResId = R.string.login_term_of_service_option_3,
            ),
        )
    }
}

@Composable
fun TermsOfServiceScreen(
    termOptions: MutableList<TermOption>,
    isButtonEnabled: Boolean,
    onCompletedButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = GoalMateDimens.HorizontalPadding),
    ) {
        Text(
            text = stringResource(R.string.login_term_of_service_title),
            style = MaterialTheme.goalMateTypography.subtitle,
        )
        Spacer(modifier = Modifier.size(27.dp))

        termOptions.forEachIndexed { index, termOption ->
            LabeledCheckbox(
                label = stringResource(termOption.contentResId),
                isChecked = termOption.isChecked,
                onCheckedChange = {
                    termOptions[index] = termOption.copy(isChecked = !termOption.isChecked)
                },
            )
            if (index != termOptions.lastIndex) {
                Spacer(modifier = Modifier.size(16.dp))
            }
        }

        Spacer(modifier = Modifier.size(41.dp))
        GoalMateButton(
            content = stringResource(R.string.login_term_of_service_btn),
            onClick = onCompletedButtonClicked,
            buttonSize = ButtonSize.LARGE,
            enabled = isButtonEnabled,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.size(GoalMateDimens.BottomMargin))
    }
}

@Composable
fun LabeledCheckbox(
    label: String,
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onCheckedChange() },
    ) {
        GoalMateCheckBox(isChecked = isChecked, onCheckedChange = onCheckedChange)
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = label, style = MaterialTheme.goalMateTypography.body)
    }
}

@Composable
@Preview(showBackground = true)
private fun TermsOfServiceScreenPreview() {
    val termOptions = remember { TermOption.DEFAULT.toMutableStateList() }
    GoalMateTheme {
        TermsOfServiceScreen(
            termOptions = termOptions,
            isButtonEnabled = false,
            onCompletedButtonClicked = {},
        )
    }
}

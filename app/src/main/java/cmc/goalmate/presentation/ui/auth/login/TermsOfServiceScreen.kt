package cmc.goalmate.presentation.ui.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.ButtonSize
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.components.GoalMateCheckBox
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.auth.login.model.TermOption
import cmc.goalmate.presentation.ui.auth.login.model.TermOptionState
import cmc.goalmate.presentation.ui.auth.login.model.TermOptionState.Companion.isAllChecked
import cmc.goalmate.presentation.ui.common.WebScreenUrl
import cmc.goalmate.presentation.ui.util.noRippleClickable

@Composable
fun TermsOfServiceScreen(
    navigateToWebScreen: (WebScreenUrl) -> Unit,
    onCompletedButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val termOptions = remember { TermOptionState.DEFAULT.toMutableStateList() }
    val isButtonEnabled = termOptions.isAllChecked()

    Column(modifier = modifier.padding(horizontal = GoalMateDimens.HorizontalPadding)) {
        Text(
            text = stringResource(R.string.login_term_of_service_title),
            style = MaterialTheme.goalMateTypography.subtitle,
        )
        Spacer(modifier = Modifier.size(27.dp))

        TermCheck(
            termOptions = termOptions,
            allCheckButtonChecked = isButtonEnabled,
            onAllCheckChange = {
                toggleAllCheckItems(
                    newCheckedState = !isButtonEnabled,
                    termOptions,
                )
            },
            onCheckedChange = { index -> toggleCheckItem(index, termOptions) },
            onDetailButtonClicked = { termUrl -> navigateToWebScreen(termUrl) },
        )

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

private fun toggleAllCheckItems(
    newCheckedState: Boolean,
    termOptions: MutableList<TermOptionState>,
) {
    termOptions.forEachIndexed { index, termOption ->
        termOptions[index] = termOption.copy(isChecked = newCheckedState)
    }
}

private fun toggleCheckItem(
    index: Int,
    termOptions: MutableList<TermOptionState>,
) {
    val currentState = termOptions[index]
    termOptions[index] = currentState.copy(isChecked = !currentState.isChecked)
}

@Composable
private fun TermCheck(
    termOptions: List<TermOptionState>,
    allCheckButtonChecked: Boolean,
    onAllCheckChange: () -> Unit,
    onCheckedChange: (index: Int) -> Unit,
    onDetailButtonClicked: (WebScreenUrl) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TitleCheckBox(
            content = stringResource(R.string.login_term_of_service_options),
            isChecked = allCheckButtonChecked,
            onCheckedChange = onAllCheckChange,
        )

        termOptions.forEachIndexed { index, termOption ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                LabeledCheckbox(
                    termOption = termOption.termOption,
                    isChecked = termOption.isChecked,
                    onCheckedChange = { onCheckedChange(index) },
                )
                if (termOption.termOption.termUrl != null) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_forward),
                        contentDescription = null,
                        modifier = Modifier
                            .size(GoalMateDimens.TermCheckBoxSize)
                            .noRippleClickable {
                                termOption.termOption.termUrl.let { url ->
                                    onDetailButtonClicked(url)
                                }
                            },
                    )
                }
            }
        }
    }
}

@Composable
private fun TitleCheckBox(
    content: String,
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.noRippleClickable { onCheckedChange() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(GoalMateDimens.TermCheckBoxTextSpacing),
    ) {
        GoalMateCheckBox(
            isChecked = isChecked,
            modifier = Modifier
                .size(28.dp)
                .padding(4.dp),
        )
        Text(
            text = content,
            style = MaterialTheme.goalMateTypography.body,
            color = MaterialTheme.goalMateColors.onBackground,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun LabeledCheckbox(
    termOption: TermOption,
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(GoalMateDimens.TermCheckBoxTextSpacing),
        modifier = modifier.clickable { onCheckedChange() },
    ) {
        Box(
            modifier = Modifier.size(GoalMateDimens.TermCheckBoxSize),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_checkbox_check),
                contentDescription = null,
                tint = if (isChecked) MaterialTheme.goalMateColors.onBackground else MaterialTheme.goalMateColors.disabled,
                modifier = Modifier.size(24.dp),
            )
        }
        Text(
            text = stringResource(termOption.contentResId),
            style = MaterialTheme.goalMateTypography.body,
            modifier = Modifier,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun TermsOfServiceScreenPreview() {
    GoalMateTheme {
        TermsOfServiceScreen(
            navigateToWebScreen = {},
            onCompletedButtonClicked = {},
        )
    }
}

package cmc.goalmate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.Grey400
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun GoalMateTextField(
    value: String,
    onValueChange: (String) -> Unit,
    canCheckDuplicate: Boolean,
    onDuplicateCheck: () -> Unit,
    modifier: Modifier = Modifier,
    inputTextState: InputTextState = InputTextState.None,
    helperText: String = "",
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        visualTransformation = VisualTransformation.None,
        textStyle = MaterialTheme.goalMateTypography.body.copy(color = inputTextState.getTextColor()),
        modifier = modifier,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
        ),
        decorationBox = { innerTextField ->
            Column(
                modifier = Modifier,
            ) {
                val borderColor = inputTextState.getBorderColor()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(30.dp))
                        .border(
                            width = 2.dp,
                            color = borderColor,
                            shape = RoundedCornerShape(30.dp),
                        )
                        .padding(
                            horizontal = 14.dp,
                            vertical = 11.dp,
                        ),
                ) {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = stringResource(R.string.login_nick_name),
                                color = Grey400,
                                style = MaterialTheme.goalMateTypography.body,
                            )
                        }
                        innerTextField()
                    }
                    DuplicationCheckButton(
                        isEnabled = canCheckDuplicate,
                        onClick = onDuplicateCheck,
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = helperText,
                    style = MaterialTheme.goalMateTypography.bodySmall,
                    color = borderColor,
                )
            }
        },
    )
}

@Composable
fun InputTextState.getTextColor(): Color =
    when (this) {
        InputTextState.Error -> MaterialTheme.goalMateColors.error
        InputTextState.Success -> MaterialTheme.goalMateColors.success
        InputTextState.None -> MaterialTheme.goalMateColors.onBackground
    }

@Composable
fun InputTextState.getBorderColor(): Color =
    when (this) {
        InputTextState.Error -> MaterialTheme.goalMateColors.error
        InputTextState.Success -> MaterialTheme.goalMateColors.success
        InputTextState.None -> MaterialTheme.goalMateColors.outline
    }

@Composable
private fun DuplicationCheckButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor =
        if (isEnabled) MaterialTheme.goalMateColors.primary else MaterialTheme.goalMateColors.disabled
    val textColor =
        if (isEnabled) MaterialTheme.goalMateColors.onPrimary else MaterialTheme.goalMateColors.onDisabled
    Text(
        text = stringResource(R.string.login_nick_name_duplicate_check),
        color = textColor,
        style = MaterialTheme.goalMateTypography.buttonLabelMedium,
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .clickable(enabled = isEnabled, onClick = onClick),
    )
}

@Composable
@Preview
private fun GoalMateTextFieldPreview() {
    var text by remember { mutableStateOf("") }
    GoalMateTheme {
        GoalMateTextField(
            value = text,
            onValueChange = { text = it },
            canCheckDuplicate = false,
            onDuplicateCheck = {},
            modifier = Modifier.fillMaxWidth(),
            inputTextState = InputTextState.None,
            helperText = "오류가 발생했습니다.",
        )
    }
}

enum class InputTextState {
    None,
    Error,
    Success,
}

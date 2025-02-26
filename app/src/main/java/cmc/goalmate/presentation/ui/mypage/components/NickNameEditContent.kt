package cmc.goalmate.presentation.ui.mypage.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.ButtonSize
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.components.GoalMateTextField
import cmc.goalmate.presentation.components.InputTextState
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.ui.mypage.MyPageAction

@Composable
fun NickNameEditContent(
    nickNameText: String,
    nickNameTextState: InputTextState,
    helperText: String,
    canCheckDuplicate: Boolean,
    isConfirmButtonEnabled: Boolean,
    onAction: (MyPageAction) -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GoalMateTextField(
            value = nickNameText,
            onValueChange = {
                onAction(MyPageAction.SetNickName(it))
            },
            canCheckDuplicate = canCheckDuplicate,
            onDuplicateCheck = {
                onAction(MyPageAction.CheckDuplication(nickNameText))
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .padding(bottom = GoalMateDimens.BottomMargin),
            inputTextState = nickNameTextState,
            helperText = helperText,
        )

        GoalMateButton(
            content = stringResource(R.string.my_page_nick_name_edit_confirm),
            onClick = {
                onAction(MyPageAction.ConfirmNickName(nickNameText))
            },
            buttonSize = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth(),
            enabled = isConfirmButtonEnabled,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun NickNameEditContentPreview() {
    val focusRequester = remember { FocusRequester() }
    GoalMateTheme {
        NickNameEditContent(
            nickNameText = "",
            nickNameTextState = InputTextState.None,
            helperText = "",
            canCheckDuplicate = true,
            onAction = {},
            focusRequester = focusRequester,
            isConfirmButtonEnabled = true,
            modifier =
                Modifier
                    .height(550.dp)
                    .padding(
                        horizontal = GoalMateDimens.HorizontalPadding,
                        vertical = GoalMateDimens.BottomMargin,
                    ).imePadding(),
        )
    }
}

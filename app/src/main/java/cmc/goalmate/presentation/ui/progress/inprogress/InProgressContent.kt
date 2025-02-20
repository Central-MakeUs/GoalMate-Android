package cmc.goalmate.presentation.ui.progress.inprogress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme

@Composable
fun InProgressScreenContent(
    state: InProgressUiState,
    onAction: (InProgressAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(
                    top = 6.dp,
                    bottom = GoalMateDimens.ItemVerticalPaddingLarge,
                )
                .verticalScroll(rememberScrollState()),
        ) {
            CalendarSection(
                weeklyProgressState = state.weeklyProgressState,
                selectedDate = state.selectedDate,
                onAction = onAction,
                modifier = Modifier.padding(horizontal = GoalMateDimens.HorizontalPadding),
            )

            Spacer(Modifier.size(GoalMateDimens.VerticalSpacerLarge))

            DailyTodoSection(
                dailyProgressState = state.selectedDailyState,
                onAction = onAction,
                modifier = Modifier,
            )

            Spacer(Modifier.size(GoalMateDimens.VerticalSpacerLarge))

            GoalInfoSection(
                goalInfoState = state.goalInfoState,
                navigateToDetail = {},
                modifier = Modifier.padding(bottom = 86.dp),
            )
        }

        GoalMateButton(
            content = "멘토 코멘트 받으러 가기",
            onClick = {
                // TODO: 코멘트 화면 이동
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    horizontal = GoalMateDimens.HorizontalPadding,
                    vertical = GoalMateDimens.BottomMargin,
                ),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun InProgressContentPreview() {
    GoalMateTheme {
        InProgressScreenContent(
            state = InProgressUiState.DUMMY,
            onAction = {},
            modifier = Modifier.background(Color.White),
        )
    }
}

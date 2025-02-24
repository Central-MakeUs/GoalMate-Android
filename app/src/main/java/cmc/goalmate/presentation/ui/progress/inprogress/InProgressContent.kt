package cmc.goalmate.presentation.ui.progress.inprogress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme

@Composable
fun InProgressScreenContent(
    state: InProgressUiState,
    onAction: (InProgressAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
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

        Spacer(Modifier.size(40.dp))

        DailyTodoSection(
            dailyProgressState = state.selectedDailyState,
            onAction = onAction,
            modifier = Modifier,
        )

        Spacer(Modifier.size(54.dp))

        GoalInfoSection(
            goalInfoState = state.goalInfoState,
            navigateToDetail = { onAction(InProgressAction.NavigateToGoalDetail) },
            modifier = Modifier.padding(bottom = 126.dp),
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

package cmc.goalmate.presentation.ui.detail

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.R
import cmc.goalmate.presentation.components.AppBarWithBackButton
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.ui.detail.components.GoalStartButton
import cmc.goalmate.presentation.ui.detail.navigation.GoalSummary
import cmc.goalmate.presentation.ui.detail.start.GoalStartScreen
import cmc.goalmate.presentation.ui.util.ObserveAsEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalDetailScreen(
    navigateBack: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToCompleted: (goalId: Int, goalInfo: GoalSummary) -> Unit,
    viewModel: GoalDetailViewModel = hiltViewModel(),
) {
    val goalDetailUiState by viewModel.state.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    ObserveAsEvent(viewModel.event) { event ->
        when (event) {
            is GoalDetailEvent.NavigateToGoalStart -> {
                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheet = false
                    }
                    navigateToCompleted(event.newGoalId, event.goalSummary)
                }
            }
            GoalDetailEvent.NavigateToLogin -> navigateToLogin()
            GoalDetailEvent.ShowGoalStartConfirmation -> {
                showBottomSheet = true
            }
        }
    }

    Column {
        AppBarWithBackButton(
            onBackButtonClicked = navigateBack,
            title = stringResource(R.string.goal_detail_title),
        )

        when (val state = goalDetailUiState) {
            is GoalDetailUiState.Success -> {
                GoalDetailScreenContent(
                    goal = state.goal,
                    isLoggedIn = state.isLoggedIn,
                    onButtonClicked = {
                        viewModel.onAction(GoalDetailAction.InitiateGoal)
                    },
                )
            }

            GoalDetailUiState.Loading -> {}

            is GoalDetailUiState.Error -> {
                Log.d("yenny", "error! -> ${state.error}")
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.goalMateColors.background,
            tonalElevation = 0.dp,
        ) {
            GoalStartScreen(
                goal = goalDetailUiState.goalSummary(),
                onStartButtonClicked = { viewModel.onAction(GoalDetailAction.ConfirmGoalStart) },
            )
        }
    }
}

@Composable
private fun GoalDetailScreenContent(
    isLoggedIn: Boolean,
    goal: GoalDetailUiModel,
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        GoalDetailContent(
            goal = goal,
            modifier = modifier.verticalScroll(rememberScrollState()),
        )

        if (!goal.isParticipated) {
            GoalStartButton(
                isLoggedIn = isLoggedIn,
                isEnabled = goal.isAvailable,
                availableSeatCount = goal.remainingCount,
                onClicked = onButtonClicked,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = GoalMateDimens.BottomMargin),
            )
        }
    }
}

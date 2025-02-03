package cmc.goalmate.presentation.ui.detail

import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.material3.BottomSheetDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.ui.detail.finish.navigation.GoalSummary
import cmc.goalmate.presentation.ui.detail.start.GoalStartScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalDetailScreen(
    navigateToCompleted: (GoalSummary) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GoalDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    GoalDetailContent(
        goal = state.goal,
        showBottomSheet = { showBottomSheet = true },
        modifier = modifier,
    )

    if (showBottomSheet) {
        val goalSummary = state.goal.toSummary()
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.goalMateColors.background,
            tonalElevation = 0.dp,
            windowInsets = BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom),
        ) {
            GoalStartScreen(
                goal = goalSummary,
                onStartButtonClicked = {
                    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                        navigateToCompleted(goalSummary)
                    }
                },
            )
        }
    }
}

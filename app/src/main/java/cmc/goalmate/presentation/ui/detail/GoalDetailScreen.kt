package cmc.goalmate.presentation.ui.detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.ui.detail.start.GoalStartScreen
import cmc.goalmate.presentation.ui.home.GoalState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: GoalDetailViewModel = hiltViewModel(),
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

//    GoalDetailContent(
//        showBottomSheet = { showBottomSheet = true },
//    )

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
        ) {
            GoalStartScreen(
                title = "목표명",
                mentorName = "멘토명",
                price = "10,000원",
                totalPrice = "0원",
                onStartButtonClicked = {
                    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                        // TODO: 목표 시작 플로우
                    }
                },
            )
        }
    }
}

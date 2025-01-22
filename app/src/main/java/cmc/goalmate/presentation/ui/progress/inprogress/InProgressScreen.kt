package cmc.goalmate.presentation.ui.progress.inprogress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.app.navigation.NavigateToGoal
import cmc.goalmate.presentation.components.AppBarWithBackButton

@Composable
fun InProgressScreen(
    navigateToComments: NavigateToGoal,
    navigateToGoalDetail: NavigateToGoal,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InProgressViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier,
    ) {
        AppBarWithBackButton(
            onBackButtonClicked = navigateBack,
            title = state.goalInfo.title,
        )

        MyGoalProgressContent(
            state = state,
            onAction = viewModel::onAction,
            navigateToGoalDetail = navigateToGoalDetail,
            navigateToComments = navigateToComments,
            showError = {
                // TODO: 에러 모달 띄우기
            },
        )
    }
}

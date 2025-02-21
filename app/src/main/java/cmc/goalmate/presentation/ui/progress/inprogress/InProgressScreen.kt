package cmc.goalmate.presentation.ui.progress.inprogress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.app.navigation.NavigateToCommentDetail
import cmc.goalmate.app.navigation.NavigateToGoal
import cmc.goalmate.presentation.components.AppBarWithBackButton

@Composable
fun InProgressScreen(
    goalTitle: String,
    navigateToComments: NavigateToCommentDetail,
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
            title = goalTitle,
        )

        InProgressScreenContent(
            state = state,
            onAction = viewModel::onAction,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

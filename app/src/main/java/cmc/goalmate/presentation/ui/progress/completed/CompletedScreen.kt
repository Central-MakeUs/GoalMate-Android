package cmc.goalmate.presentation.ui.progress.completed

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cmc.goalmate.app.navigation.NavigateToGoal
import cmc.goalmate.presentation.components.AppBarWithBackButton
import cmc.goalmate.presentation.ui.progress.completed.model.CompletedGoalUiModel

@Composable
fun CompletedScreen(
    navigateToComments: NavigateToGoal,
    navigateToGoalDetail: NavigateToGoal,
    navigateToHome: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        AppBarWithBackButton(
            onBackButtonClicked = navigateBack,
            title = "목표 완료",
        )
        // TODO : 뷰모델 사용 여부 보류 - 서버와 이야기 해보기
        MyGoalCompletedContent(
            completedGoal = CompletedGoalUiModel.DUMMY,
            navigateToComments = navigateToComments,
            navigateToHome = navigateToHome,
            navigateToGoalDetail = navigateToGoalDetail,
        )
    }
}

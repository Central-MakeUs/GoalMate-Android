package cmc.goalmate.presentation.ui.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import cmc.goalmate.app.navigation.InProgressGoalParams
import cmc.goalmate.app.navigation.Screen
import cmc.goalmate.app.navigation.navigateToInProgress
import cmc.goalmate.presentation.ui.auth.navigation.navigateToLogin
import cmc.goalmate.presentation.ui.detail.GoalDetailScreen
import cmc.goalmate.presentation.ui.detail.finish.PaymentCompletedScreen
import kotlin.reflect.typeOf

fun NavGraphBuilder.detailNavGraph(navController: NavController) {
    navigation<Screen.GoalDetail>(
        startDestination = Screen.GoalDetail.Detail::class,
    ) {
        composable<Screen.GoalDetail.Detail> {
            GoalDetailScreen(
                navigateBack = { navController.navigateUp() },
                navigateToLogin = { navController.navigateToLogin() },
                navigateToCompleted = navController::navigateToPaymentCompleted,
            )
        }

        composable<Screen.GoalDetail.PaymentCompleted>(
            typeMap = mapOf(typeOf<GoalSummary>() to GoalSummaryType),
        ) { backStackEntry ->
            val content = backStackEntry.toRoute<Screen.GoalDetail.PaymentCompleted>()
            PaymentCompletedScreen(
                goal = content.goalSummary,
                navigateToAchievingGoal = {
                    navController.navigateToInProgress(
                        params = InProgressGoalParams(
                            menteeGoalId = content.newGoalId,
                            goalTitle = content.goalSummary.title,
                            roomId = content.newCommentRoomId,
                            goalId = content.goalSummary.goalId,
                        ),
                    ) {
                        popUpTo<Screen.GoalDetail> { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onBackPressed = { navController.popBackStack() },
            )
        }
    }
}

fun NavController.navigateToDetail(goalId: Int) {
    navigate(Screen.GoalDetail.Detail(goalId = goalId))
}

data class PaymentCompletedParams(
    val goalId: Int,
    val commentRoomId: Int,
    val goalSummary: GoalSummary,
)

fun NavController.navigateToPaymentCompleted(params: PaymentCompletedParams) {
    navigate(
        Screen.GoalDetail.PaymentCompleted(
            newGoalId = params.goalId,
            newCommentRoomId = params.commentRoomId,
            goalSummary = params.goalSummary,
        ),
    )
}

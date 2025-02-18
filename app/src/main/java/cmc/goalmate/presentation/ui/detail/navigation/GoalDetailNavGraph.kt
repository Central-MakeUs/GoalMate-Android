package cmc.goalmate.presentation.ui.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import cmc.goalmate.app.navigation.Screen
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
                navigateToCompleted = { goaId, goalInfo ->
                    navController.navigateToPaymentCompleted(goaId, goalInfo)
                },
            )
        }

        composable<Screen.GoalDetail.PaymentCompleted>(
            typeMap = mapOf(typeOf<GoalSummary>() to GoalSummaryType),
        ) { backStackEntry ->
            val content = backStackEntry.toRoute<Screen.GoalDetail.PaymentCompleted>()
            PaymentCompletedScreen(
                goal = content.goalSummary,
                navigateToAchievingGoal = {
                    // TODO: 목표 시작 화면 -> content.newGoalId로 연결!
                },
            )
        }
    }
}

fun NavController.navigateToDetail(goalId: Int) {
    navigate(Screen.GoalDetail.Detail(goalId = goalId))
}

fun NavController.navigateToPaymentCompleted(
    goalId: Int,
    goalSummary: GoalSummary,
) {
    navigate(Screen.GoalDetail.PaymentCompleted(newGoalId = goalId, goalSummary = goalSummary))
}

package cmc.goalmate.presentation.ui.detail.finish.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import cmc.goalmate.app.navigation.Screen
import cmc.goalmate.presentation.ui.detail.finish.PaymentCompletedScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

fun NavController.navigateToPaymentCompleted(goalSummary: GoalSummary) {
    navigate(Screen.PaymentCompleted(goalSummary = goalSummary))
}

fun NavGraphBuilder.paymentCompleted(navigateToAchievingGoal: () -> Unit) {
    composable<Screen.PaymentCompleted>(
        typeMap = mapOf(typeOf<GoalSummary>() to GoalSummaryType),
    ) { backStackEntry ->
        val content = backStackEntry.toRoute<Screen.PaymentCompleted>()
        PaymentCompletedScreen(
            goal = content.goalSummary,
            navigateToAchievingGoal = navigateToAchievingGoal,
        )
    }
}

@Serializable
data class GoalSummary(
    val title: String,
    val mentor: String,
    val price: String,
    val totalPrice: String,
)

val GoalSummaryType = object : NavType<GoalSummary>(isNullableAllowed = false) {
    override fun get(
        bundle: Bundle,
        key: String,
    ): GoalSummary? = bundle.getString(key)?.let { Json.decodeFromString(it) }

    override fun parseValue(value: String): GoalSummary = Json.decodeFromString(value)

    override fun put(
        bundle: Bundle,
        key: String,
        value: GoalSummary,
    ) {
        bundle.putString(key, Json.encodeToString(GoalSummary.serializer(), value))
    }

    override fun serializeAsValue(value: GoalSummary): String = Json.encodeToString(GoalSummary.serializer(), value)
}

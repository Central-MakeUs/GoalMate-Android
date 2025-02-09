package cmc.goalmate.presentation.ui.detail.navigation

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

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

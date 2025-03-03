package cmc.goalmate.presentation.ui.util

import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlin.coroutines.coroutineContext

object EventBus {
    private val _events = MutableSharedFlow<GoalMateEvent>()
    val events = _events.asSharedFlow()

    suspend fun postEvent(event: GoalMateEvent) {
        _events.emit(event)
    }

    suspend inline fun <reified T> subscribeEvent(crossinline onEvent: (T) -> Unit) {
        events.filterIsInstance<T>()
            .collectLatest { event ->
                coroutineContext.ensureActive()
                onEvent(event)
            }
    }
}

sealed class GoalMateEvent() {
    data class TodoCheckChanged(val menteeGoalId: Int, val remainingTodos: Int) : GoalMateEvent()

    data object ReadComment : GoalMateEvent()

    data class RemainingTodayGoalCount(val count: Int) : GoalMateEvent()

    data object StartNewGoal : GoalMateEvent()
}

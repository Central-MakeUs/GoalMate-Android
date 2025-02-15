package cmc.goalmate.presentation.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import cmc.goalmate.app.navigation.Screen
import cmc.goalmate.domain.repository.UserRepository
import cmc.goalmate.presentation.ui.common.LoginStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed interface GoalDetailUiState {
    data object Loading : GoalDetailUiState

    data class Success(val isLoggedIn: Boolean, val goal: GoalDetailUiModel) : GoalDetailUiState
}

fun GoalDetailUiState.getGoalOrNull(): GoalDetailUiModel? = (this as? GoalDetailUiState.Success)?.goal

@HiltViewModel
class GoalDetailViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        userRepository: UserRepository,
    ) : LoginStateViewModel(userRepository) {
        private val goalId = savedStateHandle.toRoute<Screen.GoalDetail.Detail>()

        private val _state = MutableStateFlow<GoalDetailUiState>(GoalDetailUiState.Loading)
        val state: StateFlow<GoalDetailUiState>
            get() = _state.asStateFlow()

        init {
            _state.value =
                GoalDetailUiState.Success(isLoggedIn = isLoggedIn.value, goal = GoalDetailUiModel.DUMMY)
        }
    }

//    private val id = savedStateHandle.toRoute<Route.Detail>().id
//
//    val detailUiState: StateFlow<DetailUiState> = getUsecase(
//        id = id
//    ).map {
//        DetailUiState.DetailInfo(it)
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5_000),
//        initialValue = DetailUiState.Loading
//    )

package cmc.goalmate.presentation.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import cmc.goalmate.app.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GoalDetailViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val goalId = savedStateHandle.toRoute<Screen.Detail>()
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

package cmc.goalmate.presentation.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import cmc.goalmate.app.navigation.Screen
import cmc.goalmate.presentation.ui.home.GoalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class GoalDetailUiState(
    val goal: GoalDetailUiModel,
) {
    companion object {
        val initialGoalUiState = GoalDetailUiState(
            goal = GoalDetailUiModel(
                title = "다온과 함께하는 영어 완전 정복 30일 목표",
                mentorName = "다온",
                imageUrls = listOf(),
                category = "영어",
                totalDates = "30일",
                startDate = "2025년 01월 01일",
                endDate = "2025년 01월 30일",
                price = "10,000원",
                discount = "100%",
                totalPrice = "0원",
                currentMembers = 7,
                maxMembers = 23,
                state = GoalState.AVAILABLE,
                description = "“영어를 하고 싶었지만 어떤 방법으로 해야 할 지, 루틴을 세우지만 어떤 방법이 효율적일지 고민이 많지 않았나요?”",
                weeklyGoal = listOf("간단한 단어부터 시작하기", "기본 문장 읽기"),
                milestone = listOf("영어로 원어민과 편안하게 대화하는 법"),
                detailImageUrl = "",
            ),
        )
    }
}

@HiltViewModel
class GoalDetailViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val goalId = savedStateHandle.toRoute<Screen.Detail>()

        private val _state = MutableStateFlow<GoalDetailUiState>(GoalDetailUiState.initialGoalUiState)
        val state: StateFlow<GoalDetailUiState>
            get() = _state
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

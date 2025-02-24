package cmc.goalmate.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cmc.goalmate.app.navigation.NavigateToGoal
import cmc.goalmate.presentation.components.LogoAppBar
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.ui.home.components.GoalItem

@Composable
fun HomeScreen(
    navigateToDetail: NavigateToGoal,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        LogoAppBar()
        when (val homeState = state) {
            HomeUiState.Loading -> {}
            is HomeUiState.Success -> {
                HomeContent(
                    goals = homeState.goals,
                    navigateToDetail = navigateToDetail,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun HomeContent(
    goals: List<GoalUiModel>,
    navigateToDetail: NavigateToGoal,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(
            start = GoalMateDimens.HorizontalPadding,
            end = GoalMateDimens.HorizontalPadding,
            bottom = 105.dp,
        ),
        horizontalArrangement = Arrangement.spacedBy(30.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp),
    ) {
        items(goals) { goal ->
            GoalItem(
                goal = goal,
                navigateToDetail = navigateToDetail,
                modifier = Modifier.width(GoalMateDimens.GoalItemWidth),
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeScreenPreview() {
    GoalMateTheme {
        HomeContent(
            goals = dummyGoals,
            navigateToDetail = {},
        )
    }
}

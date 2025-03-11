package cmc.goalmate.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmc.goalmate.presentation.components.ErrorScreen
import cmc.goalmate.presentation.components.LogoAppBar
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.ui.home.components.GoalItem
import cmc.goalmate.presentation.ui.main.navigation.NavigateToGoal

@Composable
fun HomeScreen(
    navigateToDetail: NavigateToGoal,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Column(
        modifier =
            modifier.background(
                MaterialTheme.goalMateColors.background,
            ),
    ) {
        LogoAppBar()
        when (val homeState = state) {
            HomeUiState.Loading -> {}
            is HomeUiState.Success -> {
                HomeContent(
                    goals = homeState.goals,
                    isRefreshing = homeState.isRefreshing,
                    onRefresh = { viewModel.onAction(HomeAction.Refresh) },
                    navigateToDetail = navigateToDetail,
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(top = GoalMateDimens.TopMargin),
                )
            }

            is HomeUiState.Error -> {
                ErrorScreen(
                    onRetryButtonClicked = { viewModel.onAction(HomeAction.Retry) },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    goals: List<GoalUiModel>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    navigateToDetail: NavigateToGoal,
    modifier: Modifier = Modifier,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    Box(
        modifier = modifier
            .pullToRefresh(
                isRefreshing = isRefreshing,
                state = pullToRefreshState,
                onRefresh = onRefresh,
            ),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier,
            contentPadding =
                PaddingValues(
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

        Indicator(
            modifier = Modifier.align(Alignment.TopCenter),
            isRefreshing = isRefreshing,
            state = pullToRefreshState,
            containerColor = MaterialTheme.goalMateColors.primary,
            color = MaterialTheme.goalMateColors.onPrimary,
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeScreenPreview() {
    GoalMateTheme {
        HomeContent(
            goals = dummyGoals,
            isRefreshing = false,
            onRefresh = {},
            navigateToDetail = {},
            modifier = Modifier.fillMaxSize().padding(top = 4.dp),
        )
    }
}

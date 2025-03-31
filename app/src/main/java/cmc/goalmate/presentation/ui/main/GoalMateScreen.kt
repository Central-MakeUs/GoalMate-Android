package cmc.goalmate.presentation.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cmc.goalmate.presentation.components.BottomNavigationBar
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.ui.main.BottomNavItem.Companion.bottomNavItemScreens
import cmc.goalmate.presentation.ui.main.navigation.GoalMateNavHost
import cmc.goalmate.presentation.ui.util.ObserveAsEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GoalMateScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: GoalMateViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isBottomBarVisible by remember {
        derivedStateOf {
            navBackStackEntry?.destination?.route?.let { it in bottomNavItemScreens } ?: true
        }
    }
    var showBottomBar by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(isBottomBarVisible) {
        if (isBottomBarVisible) {
            viewModel.updateComments()
            delay(200L)
            showBottomBar = true
        } else {
            showBottomBar = false
        }
    }

    ObserveAsEvent(viewModel.event) { event ->
        when (event) {
            MainEvent.ShowErrorMessage -> {
                scope.launch {
                    snackbarHostState.showSnackbar("골메이트 서비스에 연결할 수 없습니다! 네트워크 연결을 확인해주세요!")
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        containerColor = MaterialTheme.goalMateColors.background,
        modifier = Modifier.imePadding(),
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(
                    navController = navController,
                    canShowPopup = state.hasRemainingTodos,
                    navItems = BottomNavItem.entries,
                    badgeState = state.badgeCounts,
                )
            }
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            GoalMateNavHost(navController = navController)
        }
    }
}

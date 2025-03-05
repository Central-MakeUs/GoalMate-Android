package cmc.goalmate.presentation.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.delay

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

    LaunchedEffect(isBottomBarVisible) {
        if (isBottomBarVisible) {
            viewModel.updateComments()
            delay(150L)
            showBottomBar = true
        } else {
            showBottomBar = false
        }
    }

    Scaffold(
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

package cmc.goalmate.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cmc.goalmate.app.navigation.GoalMateNavHost
import cmc.goalmate.presentation.components.BottomNavItem.Companion.bottomNavItemScreens
import cmc.goalmate.presentation.components.BottomNavigationBar
import cmc.goalmate.presentation.components.LogoAppBar
import cmc.goalmate.presentation.theme.GoalMateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoalMateTheme {
                GoalMateScreen()
            }
        }
    }
}

@Composable
private fun GoalMateScreen(navController: NavHostController = rememberNavController()) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isBottomBarVisible by remember {
        derivedStateOf {
            navBackStackEntry?.destination?.route in bottomNavItemScreens
        }
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            if (isBottomBarVisible) {
                LogoAppBar(modifier = Modifier.padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()))
            }
        },
        bottomBar = {
            if (isBottomBarVisible) {
                BottomNavigationBar(navController)
            }
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
        ) {
            GoalMateNavHost(navController = navController)
        }
    }
}

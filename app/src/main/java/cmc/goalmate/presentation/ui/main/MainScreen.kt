package cmc.goalmate.presentation.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.home.HomeScreen
import cmc.goalmate.presentation.ui.mygoals.MyGoalsScreen
import cmc.goalmate.presentation.ui.mypage.MyPageScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController) },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen,
        ) {
            composable<Screen.HomeScreen> {
                HomeScreen()
            }
            composable<Screen.MyGoalsScreen> {
                MyGoalsScreen()
            }
            composable<Screen.MyPageScreen> {
                MyPageScreen()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.goalMateColors.surface,
        tonalElevation = 0.dp,
    ) {
        BottomNavItem.entries.forEach { navItem ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.hasRoute(navItem.route::class) } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = {
                    Text(
                        text = stringResource(navItem.title),
                        style = MaterialTheme.goalMateTypography.caption,
                    )
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(navItem.icon),
                        contentDescription = stringResource(navItem.title),
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.goalMateColors.selected,
                    selectedTextColor = MaterialTheme.goalMateColors.selected,
                    unselectedIconColor = MaterialTheme.goalMateColors.unSelected,
                    unselectedTextColor = MaterialTheme.goalMateColors.unSelected,
                    indicatorColor = MaterialTheme.goalMateColors.surface,
                ),
            )
        }
    }
}

enum class BottomNavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: Screen,
) {
    Home(title = R.string.home, icon = R.drawable.home, route = Screen.HomeScreen),
    MY_GOALS(title = R.string.my_goals, icon = R.drawable.goal, route = Screen.MyGoalsScreen),
    MY_PAGE(title = R.string.my_page, icon = R.drawable.my, route = Screen.MyPageScreen),
}

@Composable
@Preview
fun MainScreenPreview() {
    GoalMateTheme {
        MainScreen()
    }
}

package cmc.goalmate.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import cmc.goalmate.R
import cmc.goalmate.app.navigation.Screen
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import kotlin.reflect.KClass

enum class BottomNavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: Screen,
) {
    HOME(title = R.string.home, icon = R.drawable.icon_home, Screen.Main.Home),
    MY_GOALS(title = R.string.my_goals, icon = R.drawable.icon_goal, Screen.Main.MyGoal),
    MY_PAGE(title = R.string.my_page, icon = R.drawable.icon_my, Screen.Main.MyPage),
    ;

    companion object {
        val bottomNavItemScreens = entries.map { it.route::class.qualifiedName }.toSet()
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavigationBar(
        containerColor = MaterialTheme.goalMateColors.surface,
        tonalElevation = 0.dp,
    ) {
        BottomNavItem.entries.forEach { navItem ->
            NavigationBarItem(
                selected = navBackStackEntry.matchesRoute(navItem.route::class),
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.startDestinationId) {
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

fun NavBackStackEntry?.matchesRoute(route: KClass<out Any>): Boolean =
    this?.destination?.hierarchy?.any {
        it.hasRoute(route)
    } == true

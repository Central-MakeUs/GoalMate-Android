package cmc.goalmate.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cmc.goalmate.R
import cmc.goalmate.app.navigation.Screen
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import kotlin.reflect.KClass
import androidx.compose.ui.window.PopupPositionProvider as PopupPositionProvider1

enum class BottomNavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: Screen,
) {
    HOME(title = R.string.home, icon = R.drawable.icon_home, Screen.Main.Home),
    MY_GOALS(title = R.string.my_goals, icon = R.drawable.icon_goal, Screen.Main.MyGoal),
    COMMENTS(title = R.string.comments, icon = R.drawable.icon_comment, Screen.Main.Comments),
    MY_PAGE(title = R.string.my_page, icon = R.drawable.icon_my, Screen.Main.MyPage),
    ;

    companion object {
        val bottomNavItemScreens = entries.map { it.route::class.qualifiedName }.toSet()
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    canShowPopup: Boolean,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var xx by rememberSaveable { mutableStateOf(0f) }
    var yy by rememberSaveable { mutableStateOf(0f) }

    NavigationBar(
        containerColor = MaterialTheme.goalMateColors.background,
        tonalElevation = 0.dp,
        modifier = modifier,
    ) {
        BottomNavItem.entries.forEach { navItem ->
            NavigationBarItem(
                selected = navBackStackEntry.matchesRoute(navItem.route::class),
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
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
                    indicatorColor = MaterialTheme.goalMateColors.background,
                ),
                modifier = Modifier.then(
                    if (navItem == BottomNavItem.MY_GOALS) {
                        Modifier
                            .onGloballyPositioned { coordinates ->
                                val anchorBounds = coordinates.boundsInWindow()
                                xx = anchorBounds.center.x
                                yy = anchorBounds.top - 90
                            }
                    } else {
                        Modifier
                    },
                ),
            )
        }
    }

    if (canShowPopup && !navBackStackEntry.matchesRoute(BottomNavItem.MY_GOALS.route::class)) {
        Popup(
            popupPositionProvider = object : PopupPositionProvider1 {
                override fun calculatePosition(
                    anchorBounds: IntRect,
                    windowSize: IntSize,
                    layoutDirection: LayoutDirection,
                    popupContentSize: IntSize,
                ): IntOffset {
                    val x = xx.toInt() - (popupContentSize.width / 2)
                    val y = yy.toInt()
                    return IntOffset(x, y)
                }
            },
            onDismissRequest = { },
            properties = PopupProperties(focusable = false),
        ) {
            Image(
                painter = painterResource(R.drawable.image_todo_balloon),
                contentDescription = null,
                modifier = Modifier.size(width = 136.dp, height = 39.dp),
            )
        }
    }
}

fun NavBackStackEntry?.matchesRoute(route: KClass<out Any>): Boolean =
    this?.destination?.hierarchy?.any {
        it.hasRoute(route)
    } == true

@Composable
@Preview(showBackground = true)
private fun BottomNavBarPreview() {
    GoalMateTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            BottomNavigationBar(
                navController = rememberNavController(),
                canShowPopup = true,
                modifier = Modifier.height(78.dp).align(Alignment.BottomCenter),
            )
        }
    }
}

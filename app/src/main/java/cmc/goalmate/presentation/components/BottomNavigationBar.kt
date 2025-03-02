package cmc.goalmate.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.home.navigation.navigateInBottomNav
import cmc.goalmate.presentation.ui.main.BottomNavItem
import cmc.goalmate.presentation.ui.main.GoalMateUiState.Companion.DEFAULT_NEW_COMMENT_COUNT
import kotlin.reflect.KClass
import androidx.compose.ui.window.PopupPositionProvider as PopupPositionProvider1

@Composable
fun BottomNavigationBar(
    navController: NavController,
    canShowPopup: Boolean,
    navItems: List<BottomNavItem>,
    modifier: Modifier = Modifier,
    badgeState: Map<BottomNavItem, Int> = mapOf(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var xx by rememberSaveable { mutableFloatStateOf(0f) }
    var yy by rememberSaveable { mutableFloatStateOf(0f) }

    NavigationBar(
        containerColor = MaterialTheme.goalMateColors.background,
        tonalElevation = 0.dp,
        modifier = modifier,
    ) {
        navItems.forEach { navItem ->
            val badgeCount = badgeState.getOrDefault(navItem, DEFAULT_NEW_COMMENT_COUNT)
            NavigationBarItem(
                selected = navBackStackEntry.matchesRoute(navItem.route::class),
                onClick = { navController.navigateInBottomNav(navItem.route) },
                label = {
                    Text(
                        text = stringResource(navItem.title),
                        style = MaterialTheme.goalMateTypography.caption,
                    )
                },
                icon = {
                    BadgedBox(badge = {
                        if (badgeCount > 0) {
                            Badge(
                                containerColor = MaterialTheme.goalMateColors.onError,
                                contentColor = MaterialTheme.goalMateColors.background,
                                modifier = Modifier.offset(x = 2.dp, y = (-3).dp),
                            ) {
                                Text(text = "$badgeCount")
                            }
                        }
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(navItem.icon),
                            contentDescription = stringResource(navItem.title),
                        )
                    }
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
                navItems = BottomNavItem.entries,
                modifier = Modifier.height(78.dp).align(Alignment.BottomCenter),
                badgeState = mapOf(BottomNavItem.COMMENTS to 2),
            )
        }
    }
}

package com.nubari.montra.general.components.app

import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.BottomNavigation
import com.nubari.montra.navigation.destinations.PrimaryDestination
import com.nubari.montra.navigation.destinations.primaryDestinations

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    destinations: List<PrimaryDestination> = primaryDestinations,
    navController: NavController
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.White,
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.navigationBars,
            applyStart = true,
            applyTop = true,
            applyEnd = true
        ),
        elevation = 0.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination

        destinations.forEach { destination ->
            if (destination.name.isEmpty()) {
                BottomNavigationItem(
                    selected = false,
                    onClick = {},
                    icon = {},
                    label = {},
                    enabled = false,
                )
            } else {
                BottomNavigationItem(
                    selected = currentRoute?.hierarchy?.any {
                        destination.rootRoute == it.route
                    } == true,
                    onClick = {
                        navController.navigate(destination.startRoute) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = destination.icon),
                            contentDescription = destination.name
                        )
                    },
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = Color(0xFFC6C6C6),
                    label = {
                        Text(text = destination.name)
                    },
                    modifier = Modifier.then(Modifier.weight(4f))
                )
            }
        }
    }
}
package com.nubari.montra.general.components.app


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.TopAppBar
import com.nubari.montra.R
import com.nubari.montra.navigation.destinations.Destination

@Composable
fun MainAppBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    backgroundColor: Color = Color.White,
    backIconColor: Color = Color.White,
    elevation: Dp = 0.dp,
    title: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    val previousRoute = navController.previousBackStackEntry?.destination?.route ?: "---"
    val hierarchy = mutableListOf<String>()
    Log.i("Main-App-Bar-previous", previousRoute)
    navController.currentBackStackEntry?.let { navBackStackEntry ->
        navBackStackEntry.destination.hierarchy
            .iterator().forEach {
                hierarchy.add(it.route ?: "empty")
                Log.i(
                    "Main-App-Bar-hierarchy",
                    it.route ?: "empty"
                )
            }
        val currentRoute = navBackStackEntry.destination
        val current = currentRoute.route ?: "--"
        Log.i("Main-App-Bar-current", current)
    }


    TopAppBar(
        modifier = modifier,
        title = {
            title()
        },

        navigationIcon = {
            Box(modifier = Modifier.width(70.dp)) {
                if (
                    navController.previousBackStackEntry != null &&
                    !hierarchy.contains("home") &&
                    !hierarchy.contains("transactions") &&
                    !hierarchy.contains("budget") &&
                    !hierarchy.contains("profile")

                ) {

                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = "Back Button",
                            tint = backIconColor
                        )
                    }

                }
            }
        },
        actions = {
            Row(modifier = Modifier.width(70.dp)) {
                actions()
            }
        },
        backgroundColor = backgroundColor,
        elevation = elevation,
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars,
            applyBottom = false,
        )
    )
}
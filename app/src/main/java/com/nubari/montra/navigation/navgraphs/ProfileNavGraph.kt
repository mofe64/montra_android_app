package com.nubari.montra.navigation.navgraphs

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.navigation.destinations.PrimaryDestination
import com.nubari.montra.profile.screens.Profile

fun NavGraphBuilder.profileNavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
) {
    navigation(
        startDestination = PrimaryDestination.Profile.startRoute,
        route = PrimaryDestination.Profile.rootRoute
    ) {
        composable(Destination.Profile.route) {
            LaunchedEffect(Unit) {
                bottomBarState.value = true
            }
            Profile()
        }
    }
}
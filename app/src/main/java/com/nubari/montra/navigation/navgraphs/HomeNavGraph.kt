package com.nubari.montra.navigation.navgraphs

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nubari.montra.home.screens.Home
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.navigation.destinations.PrimaryDestination

@ExperimentalPagerApi
fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>
) {
    navigation(
        startDestination = PrimaryDestination.Home.startRoute,
        route = PrimaryDestination.Home.rootRoute
    ) {
        composable(Destination.Home.route) {
            LaunchedEffect(Unit) {
                bottomBarState.value = true
            }
            Home(navController = navController)
        }
    }
}
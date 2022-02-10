package com.nubari.montra.navigation.navhosts

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nubari.montra.accountsetup.screens.AccountSetupPrompt
import com.nubari.montra.home.screens.Home
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.navigation.destinations.PrimaryDestination
import com.nubari.montra.preferences

@Composable
fun NavigationHost(
    navController: NavHostController
) {
    val userHasOnboarded = preferences.hasOnboarded
    var startDestination = PrimaryDestination.Home.route
    if (!userHasOnboarded) {
        startDestination = Destination.SetupAccountPromptDestination.route
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destination.SetupAccountPromptDestination.route) {
            AccountSetupPrompt(navController = navController)
        }
        composable(PrimaryDestination.Home.route) {
            Home()
        }
    }
}
package com.nubari.montra.navigation.navhosts

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nubari.montra.accountsetup.screens.AccountSetupComplete
import com.nubari.montra.accountsetup.screens.AccountSetupPrompt
import com.nubari.montra.accountsetup.screens.SetupAccount
import com.nubari.montra.home.screens.Home
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.navigation.destinations.PrimaryDestination
import com.nubari.montra.preferences

@ExperimentalComposeUiApi
@Composable
fun NavigationHost(
    navController: NavHostController
) {
    val userHasOnboarded = preferences.hasOnboarded
    var startDestination = PrimaryDestination.Home.rootRoute
    if (!userHasOnboarded) {
        startDestination = PrimaryDestination.AccountSetup.rootRoute
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        navigation(
            startDestination = PrimaryDestination.AccountSetup.startRoute,
            route = PrimaryDestination.AccountSetup.rootRoute
        ) {
            composable(PrimaryDestination.AccountSetup.startRoute) {
                AccountSetupPrompt(navController = navController)
            }
            composable(Destination.AccountSetupFormDestination.route) {
                SetupAccount(navController = navController)
            }
            composable(Destination.AccountSetupCompleteDestination.route) {
                AccountSetupComplete(navController = navController)
            }
        }
        navigation(
            startDestination = PrimaryDestination.Home.startRoute,
            route = PrimaryDestination.Home.rootRoute
        ) {
            composable(PrimaryDestination.Home.startRoute) {
                Home()
            }
            composable("hello") {
                Home()
            }
        }
    }
}
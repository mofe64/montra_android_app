package com.nubari.montra.navigation.navgraphs

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nubari.montra.accountsetup.screens.AccountSetupComplete
import com.nubari.montra.accountsetup.screens.AccountSetupPrompt
import com.nubari.montra.accountsetup.screens.SetupAccount
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.navigation.destinations.PrimaryDestination

@ExperimentalComposeUiApi
fun NavGraphBuilder.accountSetupNavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>
) {
    navigation(
        startDestination = PrimaryDestination.AccountSetup.startRoute,
        route = PrimaryDestination.AccountSetup.rootRoute
    ) {
        composable(PrimaryDestination.AccountSetup.startRoute) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            AccountSetupPrompt(navController = navController)
        }
        composable(Destination.AccountSetupFormDestination.route) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            SetupAccount(navController = navController)
        }
        composable(Destination.AccountSetupCompleteDestination.route) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            AccountSetupComplete(navController = navController)
        }
    }
}
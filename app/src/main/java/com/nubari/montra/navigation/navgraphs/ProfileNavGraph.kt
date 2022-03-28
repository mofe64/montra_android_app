package com.nubari.montra.navigation.navgraphs

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.navigation.destinations.PrimaryDestination
import com.nubari.montra.profile.screens.*

@ExperimentalMaterialApi
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
            Profile(
                navController = navController
            )

        }
        composable(Destination.UserAccounts.route) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            UserAccounts(navController = navController)
        }
        composable(Destination.AllSettings.route) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            AllSettings(navController = navController)
        }
        composable(Destination.LanguageSettings.route) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            LanguageSettings(navController = navController)
        }
        composable(Destination.CurrencySettings.route) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            Currency(navController = navController)
        }
        composable(Destination.NotificationSettings.route) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            NotificationSettings(navController = navController)
        }
        composable(Destination.ExportDataForm.route) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            ExportDataForm(navController = navController)
        }
        composable(Destination.ExportDataSuccess.route) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            ExportSuccess(navController = navController)
        }

    }
}
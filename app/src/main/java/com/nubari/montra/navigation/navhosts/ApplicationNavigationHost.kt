package com.nubari.montra.navigation.navhosts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nubari.montra.accountsetup.screens.AccountSetupComplete
import com.nubari.montra.accountsetup.screens.AccountSetupPrompt
import com.nubari.montra.accountsetup.screens.SetupAccount
import com.nubari.montra.budget.screens.Budget
import com.nubari.montra.home.screens.Home
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.navigation.destinations.PrimaryDestination
import com.nubari.montra.preferences
import com.nubari.montra.profile.screens.Profile
import com.nubari.montra.transaction.screens.Transaction

@ExperimentalPagerApi
@ExperimentalComposeUiApi
@Composable
fun NavigationHost(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>
) {
    val userHasOnboarded = preferences.hasOnboarded
    var startDestination = PrimaryDestination.Home.rootRoute
    if (!userHasOnboarded) {
        startDestination = PrimaryDestination.AccountSetup.rootRoute
    }
    NavHost(
        navController = navController,
        startDestination = startDestination,
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

        navigation(
            startDestination = PrimaryDestination.Home.startRoute,
            route = PrimaryDestination.Home.rootRoute
        ) {
            composable(PrimaryDestination.Home.startRoute) {
                LaunchedEffect(Unit) {
                    bottomBarState.value = true
                }
                Home(navController = navController)
            }
        }

        navigation(
            startDestination = PrimaryDestination.Transaction.startRoute,
            route = PrimaryDestination.Transaction.rootRoute
        ) {
            composable(PrimaryDestination.Transaction.startRoute) {
                LaunchedEffect(Unit) {
                    bottomBarState.value = true
                }
                Transaction()
            }
        }

        navigation(
            startDestination = PrimaryDestination.Budget.startRoute,
            route = PrimaryDestination.Budget.rootRoute
        ) {
            composable(PrimaryDestination.Budget.startRoute) {
                LaunchedEffect(Unit) {
                    bottomBarState.value = true
                }
                Budget()
            }
        }

        navigation(
            startDestination = PrimaryDestination.Profile.startRoute,
            route = PrimaryDestination.Profile.rootRoute
        ) {
            composable(PrimaryDestination.Profile.startRoute) {
                LaunchedEffect(Unit) {
                    bottomBarState.value = true
                }
                Profile()
            }
        }
    }
}
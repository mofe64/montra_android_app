package com.nubari.montra.navigation.navhosts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.navigation.destinations.PrimaryDestination
import com.nubari.montra.navigation.navgraphs.*
import com.nubari.montra.preferences
import com.nubari.montra.transaction.screens.*
import com.nubari.montra.transaction.viewmodels.TransactionReportViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
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
    val transactionReportViewModel: TransactionReportViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        /**
         * The New Transaction route is a global destination and
         * should not be bound to any navigation graph
         * **/
        composable(
            route = Destination.NewTransaction.route + "?isExpense={isExpense}",
            arguments = listOf(
                navArgument(
                    name = "isExpense",
                ) {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            val isExpense = it.arguments?.getBoolean("isExpense") ?: false
            NewTransaction(navController = navController, isExpense = isExpense)
        }
        accountSetupNavGraph(navController = navController, bottomBarState = bottomBarState)

        homeNavGraph(navController = navController, bottomBarState = bottomBarState)

        transactionNavGraph(
            navController = navController,
            bottomBarState = bottomBarState,
            transactionReportViewModel = transactionReportViewModel
        )

        budgetNavGraph(navController = navController, bottomBarState = bottomBarState)

        profileNavGraph(navController = navController, bottomBarState = bottomBarState)
    }
}
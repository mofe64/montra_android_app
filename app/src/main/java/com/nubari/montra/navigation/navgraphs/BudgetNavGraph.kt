package com.nubari.montra.navigation.navgraphs

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nubari.montra.budget.screens.Budget
import com.nubari.montra.budget.screens.BudgetForm
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.navigation.destinations.PrimaryDestination

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
fun NavGraphBuilder.budgetNavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
) {
    navigation(
        startDestination = PrimaryDestination.Budget.startRoute,
        route = PrimaryDestination.Budget.rootRoute
    ) {
        composable(Destination.Budget.route) {
            LaunchedEffect(Unit) {
                bottomBarState.value = true
            }
            Budget(
                navController = navController
            )
        }
        composable(Destination.BudgetForm.route) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            BudgetForm(navController = navController)
        }
    }
}
package com.nubari.montra.navigation.navgraphs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.navigation.destinations.PrimaryDestination
import com.nubari.montra.transaction.screens.Transaction
import com.nubari.montra.transaction.screens.TransactionDetail
import com.nubari.montra.transaction.screens.TransactionReport
import com.nubari.montra.transaction.screens.TransactionReportPreview
import com.nubari.montra.transaction.viewmodels.TransactionReportViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
fun NavGraphBuilder.transactionNavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    transactionReportViewModel: TransactionReportViewModel
) {
    navigation(
        startDestination = PrimaryDestination.Transaction.startRoute,
        route = PrimaryDestination.Transaction.rootRoute
    ) {

        composable(Destination.Transactions.route) {
            LaunchedEffect(Unit) {
                bottomBarState.value = true
            }
            Transaction(
                navController = navController
            )
        }
        composable(Destination.TransactionReportPreview.route) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            TransactionReportPreview(
                navController = navController,
                viewModel = transactionReportViewModel
            )
        }
        composable(Destination.TransactionReport.route) {
            LaunchedEffect(Unit) {
                bottomBarState.value = true
            }
            TransactionReport(
                navController = navController,
                viewModel = transactionReportViewModel,
            )
        }
        composable(
            route = Destination.TransactionDetail.route + "?txId={txId}",
            arguments = listOf(
                navArgument(
                    name = "txId"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            TransactionDetail(
                navController = navController
            )
        }
    }
}
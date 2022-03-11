package com.nubari.montra.transaction.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.nubari.montra.R
import com.nubari.montra.data.local.models.enums.TransactionType
import com.nubari.montra.general.components.app.MainAppBar
import com.nubari.montra.general.util.Util
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.transaction.components.transactions.FilterBottomModal
import com.nubari.montra.transaction.components.transactions.TransactionTile
import com.nubari.montra.transaction.viewmodels.TransactionsViewModel
import com.nubari.montra.ui.theme.*
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun Transaction(
    navController: NavController,
    transactionsViewModel: TransactionsViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
    val state = transactionsViewModel.state.value
    val coroutineScope = rememberCoroutineScope()

    fun dismissModal() {
        coroutineScope.launch {
            if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainAppBar(
                navController = navController,
                title = {
                    Text(
                        text = "Transactions",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                },
                backgroundColor = Color.White,
                backIconColor = Color.Black
            ) {
                IconButton(onClick = {
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        } else {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.sort),
                        contentDescription = "Filter transactions button",
                        tint = Color.Black
                    )
                }
            }
        }
    ) { scaffoldPaddingValues ->
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                FilterBottomModal(
                    viewModel = transactionsViewModel,
                    state = state,
                    dismiss = {
                        dismissModal()
                    }
                )
            },
            sheetPeekHeight = 0.dp,
            sheetBackgroundColor = light80,
            sheetElevation = 20.dp,
            sheetShape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(scaffoldPaddingValues)
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15))
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(
                                Destination.TransactionReportPreview.route
                            )
                        }
                        .height(50.dp)
                        .background(violet40)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "See your financial report",
                        color = violet100
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_right_2),
                        contentDescription = "",
                        tint = violet100
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.transactions) { tx ->
                        TransactionTile(
                            colorPair =
                            if (tx.type == TransactionType.EXPENSE) {
                                Pair(red20, red100)
                            } else {
                                Pair(green20, green100)
                            },
                            icon = Util.iconMap[tx.categoryName] ?: R.drawable.rocket_launch,
                            name = tx.categoryName,
                            description = tx.description,
                            isExpense = tx.type == TransactionType.EXPENSE,
                            amount = tx.amount.toPlainString(),
                            toDetail = {
                                val route = Destination.TransactionDetail.route +
                                        "?txId=${tx.id}"
                                navController.navigate(route)
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

            }
        }
    }
}

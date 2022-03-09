package com.nubari.montra.transaction.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.nubari.montra.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nubari.montra.data.local.models.enums.TransactionType
import com.nubari.montra.data.models.CategoryBreakdown
import com.nubari.montra.general.components.app.MainAppBar
import com.nubari.montra.general.components.input.DropDown
import com.nubari.montra.transaction.components.transactionreport.DonutChart
import com.nubari.montra.transaction.components.transactionreport.ExpenseIncomeSection
import com.nubari.montra.transaction.components.transactionreport.LineChart
import com.nubari.montra.transaction.events.TransactionReportEvent
import com.nubari.montra.transaction.viewmodels.TransactionReportViewModel
import com.nubari.montra.ui.theme.*

val colors = listOf(
    violet100,
    red100,
    green100,
    yellow100,
    blue100,
    dark75
)

private object TransactionReportUtil {
    fun generateExpenseCategoriesList(list: List<CategoryBreakdown>): List<CategoryBreakdown> {
        return list.filter { it.txType == TransactionType.EXPENSE }
    }

    fun generateColorInt(): Int {
        return ((Math.random() * 16777215).toInt() or (0xFF shl 24))
    }

    fun generateIncomeCategoriesList(list: List<CategoryBreakdown>): List<CategoryBreakdown> {
        return list.filter { it.txType == TransactionType.INCOME }
    }

    // TODO look into generating colors from a pallete
    fun generateColors(number: Int): List<Color> {
        val colors = mutableListOf<Color>()
        for (i in 1..number) {
            var colorInt = generateColorInt()
            var color = Color(color = colorInt)
            while (colors.contains(color)) {
                colorInt = generateColorInt()
                color = Color(color = colorInt)
            }
            colors.add(color)
        }
        return colors
    }
}

@ExperimentalFoundationApi
@Composable
fun TransactionReport(
    navController: NavController,
    viewModel: TransactionReportViewModel,
) {
    val state = viewModel.state.value
    val systemUiController: SystemUiController = rememberSystemUiController()
    LaunchedEffect(Unit) {
        systemUiController.isNavigationBarVisible = true
    }
    val activeGraph = state.activeGraph

    val expenseCategoriesList =
        TransactionReportUtil.generateExpenseCategoriesList(state.categoryBreakDown)
    val expenseColors = remember {
        TransactionReportUtil.generateColors(number = expenseCategoriesList.size)
    }
    val incomeCategoriesList =
        TransactionReportUtil.generateIncomeCategoriesList(state.categoryBreakDown)
    val incomeColors = remember {
        TransactionReportUtil.generateColors(number = incomeCategoriesList.size)
    }

    Scaffold(
        topBar = {
            MainAppBar(
                navController = navController,
                title = {
                    Text(
                        text = "Financial Reports",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                backIconColor = Color.Black
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(top = 10.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DropDown(
                    updateValue = {},
                    options = listOf("Month"),
                    startingIndex = 0,
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = violet20,
                            shape = RoundedCornerShape(40)
                        )
                        .padding(start = 10.dp, end = 10.dp)
                )
                Row {
                    Box(
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                            )
                            .background(
                                color = if (activeGraph == "line") {
                                    violet100
                                } else {
                                    Color.White
                                }
                            )
                    ) {
                        IconButton(onClick = {
                            viewModel.createEvent(
                                TransactionReportEvent.ChangeActiveGraph("line")
                            )
                            viewModel.createEvent(
                                TransactionReportEvent.SwitchedActiveTabView("Transactions")
                            )
                        }) {
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.line_chart_icon
                                ),
                                contentDescription = "",
                                tint = if (activeGraph == "line") {
                                    Color.White
                                } else {
                                    violet100
                                }
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                            )
                            .background(
                                color = if (activeGraph == "pie") {
                                    violet100
                                } else {
                                    Color.White
                                }
                            )
                    ) {
                        IconButton(onClick = {
                            viewModel.createEvent(
                                TransactionReportEvent.ChangeActiveGraph("pie")
                            )
                            viewModel.createEvent(
                                TransactionReportEvent.SwitchedActiveTabView("Categories")
                            )
                        }) {
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.pie_chart_icon
                                ),
                                contentDescription = "",
                                tint = if (activeGraph == "pie") {
                                    Color.White
                                } else {
                                    violet100
                                }
                            )
                        }
                    }


                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            if (activeGraph == "line") {
                LineChart(
                    data = if (state.activeTab == "Income") {
                        state.incomeSpendingData
                    } else {
                        state.expenseSpendingData
                    },
                    totalValue = if (state.activeTab == "Income") {
                        state.monthIncome
                    } else {
                        state.monthExpenses
                    },
                    pathColor = if (state.activeTab == "Income") {
                        green100
                    } else {
                        redLight
                    },
                    fillColor = if (state.activeTab == "Income") {
                        green100
                    } else {
                        red
                    },
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    DonutChart(
                        data = if (state.activeTab == "Income") {
                            state.categoryIncomePieChartData
                        } else {
                            state.categoryExpensePieChartData
                        },
                        colors = if (state.activeTab == "Income") {
                            incomeColors
                        } else {
                            expenseColors
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            ExpenseIncomeSection(
                expenseCategoryBreakdowns = expenseCategoriesList,
                incomeCategoryBreakdowns = incomeCategoriesList,
                incomeColors = incomeColors,
                expenseColors = expenseColors,
                activeTabView = state.activeTabView,
                updateActiveTab = { tab ->
                    viewModel.createEvent(
                        TransactionReportEvent.SwitchedActiveTab(tab)
                    )
                },
                expenses = state.expenses,
                income = state.income,
                sortDir = state.sortDir,
                navController = navController,
                updateSortDir = { dir ->
                    viewModel.createEvent(
                        TransactionReportEvent.ChangeSortDirection(dir)
                    )
                }
            )
        }
    }
}






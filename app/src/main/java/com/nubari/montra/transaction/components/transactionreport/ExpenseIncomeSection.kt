package com.nubari.montra.transaction.components.transactionreport

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nubari.montra.R
import com.nubari.montra.data.local.models.enums.TransactionType
import com.nubari.montra.general.components.input.DropDown
import com.nubari.montra.general.util.Util
import com.nubari.montra.transaction.components.transactions.TransactionTile
import com.nubari.montra.transaction.events.TransactionReportEvent
import com.nubari.montra.transaction.viewmodels.TransactionReportViewModel
import com.nubari.montra.ui.theme.*

@Composable
fun ExpenseIncomeSection(
    viewModel: TransactionReportViewModel
) {
    val state = viewModel.state.value
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    ) {
        val tabs = listOf("Expense", "Income")
        var selectedTab by remember {
            mutableStateOf(0)
        }
        TabRow(
            selectedTabIndex = selectedTab,
            backgroundColor = light80,
            indicator = {},
            divider = {}
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTab == index) {
                                light80
                            } else {
                                Color.Black
                            },
                            fontSize = 16.sp
                        )
                    },
                    selected = selectedTab == index,
                    onClick = {
                        selectedTab = index
                        viewModel.createEvent(
                            TransactionReportEvent.SwitchedActiveTab(tabs[index])
                        )
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            color = if (selectedTab == index) {
                                violet100
                            } else {
                                light80
                            }
                        )
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val dropDownOptions = listOf("Transaction", "Category")
            DropDown(
                updateValue = { optionIndex ->
                    viewModel.createEvent(
                        TransactionReportEvent.SwitchedActiveTabView(
                            dropDownOptions[optionIndex]
                        )
                    )
                },
                options = dropDownOptions,
                startingIndex = 0,
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = violet20,
                        shape = RoundedCornerShape(40)
                    )
                    .padding(start = 10.dp, end = 10.dp)
            )
            var sortDir by remember {
                mutableStateOf("desc")
            }
            val rotation by animateFloatAsState(
                if (sortDir == "asc") {
                    180f
                } else {
                    0f
                }
            )
            IconButton(onClick = {
                sortDir = if (sortDir == "asc") {
                    "desc"
                } else {
                    "asc"
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.sort_highest_lowest),
                    contentDescription = "sort transactions in $sortDir order",
                    modifier = Modifier.rotate(rotation)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            when (selectedTab) {
                0 -> {
                    items(state.expenses) { tx ->
                        if (state.activeTabView == "Transaction") {
                            TransactionTile(
                                colorPair =
                                if (tx.type == TransactionType.EXPENSE) {
                                    Pair(red20, red100)
                                } else {
                                    Pair(green20, green100)
                                },
                                icon = Util.iconMap[tx.categoryName]
                                    ?: R.drawable.rocket_launch,
                                name = tx.categoryName,
                                description = tx.description,
                                isExpense = tx.type == TransactionType.EXPENSE,
                                amount = tx.amount.toPlainString()
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        } else {
                            CategoryTile()
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
                1 -> {
                    items(state.income) { tx ->
                        if (state.activeTabView == "Transaction") {
                            TransactionTile(
                                colorPair =
                                if (tx.type == TransactionType.EXPENSE) {
                                    Pair(red20, red100)
                                } else {
                                    Pair(green20, green100)
                                },
                                icon = Util.iconMap[tx.categoryName]
                                    ?: R.drawable.rocket_launch,
                                name = tx.categoryName,
                                description = tx.description,
                                isExpense = tx.type == TransactionType.EXPENSE,
                                amount = tx.amount.toPlainString()
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        } else {
                            CategoryTile()
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }

        }
    }
}
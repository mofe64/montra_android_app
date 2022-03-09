package com.nubari.montra.transaction.components.transactionreport

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nubari.montra.R
import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.data.local.models.enums.TransactionType
import com.nubari.montra.data.models.CategoryBreakdown
import com.nubari.montra.general.util.Util
import com.nubari.montra.transaction.components.transactions.TransactionTile
import com.nubari.montra.ui.theme.*

private const val tag = "Expense-Income-Section"

@SuppressLint("LongLogTag")
@Composable
fun ExpenseIncomeSection(

    expenseCategoryBreakdowns: List<CategoryBreakdown>,
    incomeCategoryBreakdowns: List<CategoryBreakdown>,
    incomeColors: List<Color>,
    expenseColors: List<Color>,
    activeTabView: String,
    updateActiveTab: (String) -> Unit,
    expenses: List<Transaction>,
    income: List<Transaction>,
    sortDir: String,
    updateSortDir: (String) -> Unit
) {


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
                        updateActiveTab(
                            tabs[index]
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = activeTabView, fontSize = 24.sp, fontWeight = FontWeight.Bold)

            val rotation by animateFloatAsState(
                if (sortDir == "asc") {
                    180f
                } else {
                    0f
                }
            )
            IconButton(onClick = {
                if (sortDir == "asc") {
                    updateSortDir("desc")
                } else {
                    updateSortDir("asc")
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
                    if (activeTabView == "Transactions") {
                        items(expenses) { tx ->
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
                        }
                    } else {
                        Log.i(
                            "$tag-expense-category-breakdown-size",
                            expenseCategoryBreakdowns.size.toString()
                        )
                        itemsIndexed(expenseCategoryBreakdowns) { index, item ->
                            CategoryBreakDownTile(
                                breakdown = item,
                                isPositive = false,
                                color = expenseColors[index]
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }


                    }
                }

                1 -> {
                    if (activeTabView == "Transactions") {
                        items(income) { tx ->
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
                        }
                    } else {
                        itemsIndexed(incomeCategoryBreakdowns) { index, item ->
                            CategoryBreakDownTile(
                                breakdown = item,
                                color = incomeColors[index],
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                        }
                    }
                }
            }
        }

    }
}

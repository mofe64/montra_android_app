package com.nubari.montra.transaction.components.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nubari.montra.transaction.events.TransactionScreenEvent
import com.nubari.montra.transaction.state.TransactionsState
import com.nubari.montra.transaction.util.TransactionFilterBy
import com.nubari.montra.transaction.util.TransactionSortBy
import com.nubari.montra.transaction.viewmodels.TransactionsViewModel
import com.nubari.montra.ui.theme.violet100
import com.nubari.montra.ui.theme.violet20

@Composable
fun FilterBottomModal(
    viewModel: TransactionsViewModel,
    state: TransactionsState,
    dismiss: () -> Unit
) {
    val isFiltering = state.filterBy != null
    val filter = state.filterBy?.name ?: ""
    val sort = state.sortBy?.name ?: "DATE_DESC"
    val inActiveColors = Pair(Color.White, Color.Black)
    val activeColors = Pair(violet20, violet100)
    val sortDirAsc = remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp
                )
            )
            .fillMaxWidth()
            .fillMaxHeight(.7f)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 15.dp, horizontal = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filter Transaction",
                    fontWeight = FontWeight(600),
                    fontSize = 16.sp
                )
                TextButton(
                    shape = RoundedCornerShape(20),
                    onClick = {
                        viewModel.createEvent(
                            TransactionScreenEvent.Reset
                        )
                        dismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = violet20,
                        contentColor = violet100
                    )
                ) {
                    Text(text = "Reset")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Filter By",
                fontWeight = FontWeight(600),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    modifier = Modifier.height(56.dp),
                    onClick = {
                        viewModel.createEvent(
                            TransactionScreenEvent.Filter(
                                TransactionFilterBy.INCOME
                            )
                        )
                        dismiss()
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = if (isFiltering && filter == "INCOME") {
                            activeColors.first
                        } else {
                            inActiveColors.first
                        },
                        contentColor = if (isFiltering && filter == "INCOME") {
                            activeColors.second
                        } else {
                            inActiveColors.second
                        },
                    )
                ) {
                    Text(text = "Income")
                }
                OutlinedButton(
                    modifier = Modifier
                        .height(56.dp),
                    onClick = {
                        viewModel.createEvent(
                            TransactionScreenEvent.Filter(
                                TransactionFilterBy.EXPENSE
                            )
                        )
                        dismiss()
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = if (isFiltering && filter == "EXPENSE") {
                            activeColors.first
                        } else {
                            inActiveColors.first
                        },
                        contentColor = if (isFiltering && filter == "EXPENSE") {
                            activeColors.second
                        } else {
                            inActiveColors.second
                        },
                    )
                ) {
                    Text(text = "Expense")
                }
                OutlinedButton(
                    modifier = Modifier.height(56.dp),
                    onClick = {
                        dismiss()
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = if (isFiltering && filter == "SUB") {
                            activeColors.first
                        } else {
                            inActiveColors.first
                        },
                        contentColor = if (isFiltering && filter == "SUB") {
                            activeColors.second
                        } else {
                            inActiveColors.second
                        },
                    )
                ) {
                    Text(text = "Subscription")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Sort By",
                fontWeight = FontWeight(600),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .defaultMinSize(
                            minHeight = 56.dp,
                            minWidth = 100.dp
                        ),
                    onClick = {
                        val sortDir = if (sortDirAsc.value) {
                            TransactionSortBy.DATE_ASC
                        } else {
                            TransactionSortBy.DATE_DESC
                        }
                        viewModel.createEvent(
                            TransactionScreenEvent.Sort(sortDir)
                        )
                        dismiss()
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor =
                        if (sort.startsWith("date", true)) {
                            activeColors.first
                        } else {
                            inActiveColors.first
                        },
                        contentColor =
                        if (sort.startsWith("date", true)) {
                            activeColors.second
                        } else {
                            inActiveColors.second
                        },
                    )
                ) {
                    Text(text = "Date")
                }
                Spacer(modifier = Modifier.width(20.dp))
                OutlinedButton(
                    modifier = Modifier.defaultMinSize(
                        minHeight = 56.dp,
                        minWidth = 100.dp
                    ),
                    onClick = {
                        val sortDir = if (sortDirAsc.value) {
                            TransactionSortBy.AMOUNT_ASC
                        } else {
                            TransactionSortBy.AMOUNT_DESC
                        }
                        viewModel.createEvent(
                            TransactionScreenEvent.Sort(sortDir)
                        )
                        dismiss()
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = if (sort.startsWith("amount", true)) {
                            activeColors.first
                        } else {
                            inActiveColors.first
                        },
                        contentColor = if (sort.startsWith("amount", true)) {
                            activeColors.second
                        } else {
                            inActiveColors.second
                        },
                    )
                ) {
                    Text(text = "Amount")
                }


            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Sort Direction",
                fontWeight = FontWeight(600),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .defaultMinSize(
                            minHeight = 56.dp,
                            minWidth = 100.dp
                        ),
                    onClick = {
                        sortDirAsc.value = !sortDirAsc.value
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = if (sort.endsWith("asc", true)) {
                            activeColors.first
                        } else {
                            inActiveColors.first
                        },
                        contentColor = if (sort.endsWith("asc", true)) {
                            activeColors.second
                        } else {
                            inActiveColors.second
                        },
                    )
                ) {
                    Text(text = "Ascending")
                }
                Spacer(modifier = Modifier.width(20.dp))
                OutlinedButton(
                    modifier = Modifier.defaultMinSize(
                        minHeight = 56.dp,
                        minWidth = 100.dp
                    ),
                    onClick = {},
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = if (sort.endsWith("desc", true)) {
                            activeColors.first
                        } else {
                            inActiveColors.first
                        },
                        contentColor = if (sort.endsWith("desc", true)) {
                            activeColors.second
                        } else {
                            inActiveColors.second
                        },
                    )
                ) {
                    Text(text = "Descending")
                }
            }
        }
    }
}
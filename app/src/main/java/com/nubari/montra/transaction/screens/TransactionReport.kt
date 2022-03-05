package com.nubari.montra.transaction.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.nubari.montra.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nubari.montra.data.local.models.enums.TransactionType
import com.nubari.montra.general.components.app.MainAppBar
import com.nubari.montra.general.components.input.DropDown
import com.nubari.montra.general.util.Util
import com.nubari.montra.home.components.SpendingFrequency
import com.nubari.montra.transaction.components.DonutChart
import com.nubari.montra.transaction.components.TransactionTile
import com.nubari.montra.ui.theme.*
import java.math.BigDecimal

val colors = listOf(
    violet100,
    red100,
    green100,
    yellow100,
    blue100,
    dark75
)

@ExperimentalFoundationApi
@Composable
fun TransactionReport(
    navController: NavController
) {

    val systemUiController: SystemUiController = rememberSystemUiController()
    LaunchedEffect(Unit) {
        systemUiController.isNavigationBarVisible = true
    }
    var activeGraph by remember {
        mutableStateOf("line")
    }
    val dummyData = listOf(450.0, 179.0, 370.4, 123.6, 600.3)
    var tile by remember {
        mutableStateOf("Transaction")
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
                Row() {
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
                        IconButton(onClick = { activeGraph = "line" }) {
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
                        IconButton(onClick = { activeGraph = "pie" }) {
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
                Line(
                    spendingData = dummyData
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    DonutChart(
                        data = dummyData.map { it.toFloat() },
                        colors = colors,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                    )
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
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
                            onClick = { selectedTab = index },
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

                    DropDown(
                        updateValue = {
                            tile = if (tile == "Transaction") {
                                "Category"
                            } else {
                                "Transaction"
                            }
                        },
                        options = listOf("Transaction", "Category"),
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
                val transactions = listOf(
                    com.nubari.montra.data.local.models.Transaction(
                        id = "1",
                        accountId = "1",
                        categoryName = "Shopping",
                        subscriptionId = null,
                        date = java.util.Date(),
                        type = TransactionType.EXPENSE,
                        categoryId = "1",
                        amount = BigDecimal.valueOf(100),
                        isRecurring = false,
                        name = "Shopping",
                        description = "Lavish",
                        attachmentRemote = null,
                        attachmentLocal = null,
                        frequency = null
                    ),
                    com.nubari.montra.data.local.models.Transaction(
                        id = "2",
                        accountId = "2",
                        categoryName = "Salary",
                        subscriptionId = null,
                        date = java.util.Date(),
                        type = TransactionType.INCOME,
                        categoryId = "1",
                        amount = BigDecimal.valueOf(100),
                        isRecurring = false,
                        name = "Shopping",
                        description = "Lavish",
                        attachmentRemote = null,
                        attachmentLocal = null,
                        frequency = null
                    ),
                    com.nubari.montra.data.local.models.Transaction(
                        id = "3",
                        accountId = "3",
                        categoryName = "Investments",
                        subscriptionId = null,
                        date = java.util.Date(),
                        type = TransactionType.INCOME,
                        categoryId = "1",
                        amount = BigDecimal.valueOf(100),
                        isRecurring = false,
                        name = "Shopping",
                        description = "Lavish",
                        attachmentRemote = null,
                        attachmentLocal = null,
                        frequency = null
                    ),
                    com.nubari.montra.data.local.models.Transaction(
                        id = "4",
                        accountId = "4",
                        categoryName = "Bills",
                        subscriptionId = null,
                        date = java.util.Date(),
                        type = TransactionType.INCOME,
                        categoryId = "1",
                        amount = BigDecimal.valueOf(100),
                        isRecurring = false,
                        name = "Shopping",
                        description = "Lavish",
                        attachmentRemote = null,
                        attachmentLocal = null,
                        frequency = null
                    ),
                    com.nubari.montra.data.local.models.Transaction(
                        id = "5",
                        accountId = "5",
                        categoryName = "Bills",
                        subscriptionId = null,
                        date = java.util.Date(),
                        type = TransactionType.EXPENSE,
                        categoryId = "1",
                        amount = BigDecimal.valueOf(100),
                        isRecurring = false,
                        name = "Shopping",
                        description = "Lavish",
                        attachmentRemote = null,
                        attachmentLocal = null,
                        frequency = null
                    )
                )
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(transactions) { tx ->
                        if (tile == "Transaction") {
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

@Composable
fun CategoryTile(
    color: Color = colors.random()
) {
    val progress = remember { Animatable(0f) }
    val y = listOf(.7f, .4f, .9f, .8f, .1f, .34f)
    LaunchedEffect(Unit) {
        progress.animateTo(
            y.random(),
            animationSpec = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color)
                        .height(20.dp)
                        .width(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Shopping")
            }
            Text(text = "- ₦2000", fontSize = 20.sp, color = red100)
        }
        Spacer(modifier = Modifier.height(5.dp))
        LinearProgressIndicator(
            color = color,
            progress = progress.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .height(10.dp)
                .clip(RoundedCornerShape(40))
        )
    }
}

@Composable
fun Line(
    spendingData: List<Double>
) {
    Text(
        text = "₦6,716,058",
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 10.dp)
    )
    SpendingFrequency(
        spendingData = spendingData,
        shouldDisplayHeader = false
    )
}

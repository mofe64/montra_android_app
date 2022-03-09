package com.nubari.montra.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nubari.montra.R
import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.data.local.models.enums.TransactionType
import com.nubari.montra.general.util.Util
import com.nubari.montra.navigation.destinations.PrimaryDestination
import com.nubari.montra.transaction.components.transactions.TransactionTile
import com.nubari.montra.ui.theme.*

val icons = Util.iconMap
val colors = listOf(
    Pair(yellow20, yellow100),
    Pair(violet20, violet100),
    Pair(red20, red100),
    Pair(blue20, blue100),
    Pair(green20, green100)
)

@Composable
fun RecentTransactions(
    recentTx: List<Transaction>,
    navController: NavController
) {
    Column(
        Modifier
            .padding(10.dp)
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Transactions",
                fontWeight = FontWeight(600),
                fontSize = 18.sp
            )
            TextButton(
                onClick = {
                    navController.navigate(
                        PrimaryDestination.Transaction.startRoute
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = violet40,
                    contentColor = violet100,
                ),
                shape = RoundedCornerShape(20),
            ) {
                Text(text = "See All")
            }

        }
        Spacer(modifier = Modifier.height(10.dp))
        if (recentTx.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .height(300.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                recentTx.forEach {
                    TransactionTile(
                        colorPair = if (it.categoryName == "Salary") {
                            Pair(green20, green100)
                        } else {
                            colors.random()
                        },
                        icon = icons[it.categoryName] ?: R.drawable.rocket_launch,
                        isExpense = it.type == TransactionType.EXPENSE,
                        amount = it.amount.toPlainString(),
                        name = it.categoryName,
                        description = it.description
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No Transactions for this period")
            }
        }

    }
}

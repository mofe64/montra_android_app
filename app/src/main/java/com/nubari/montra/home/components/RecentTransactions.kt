package com.nubari.montra.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.nubari.montra.R
import com.nubari.montra.auth.events.AuthUIEvent
import com.nubari.montra.transaction.components.TransactionTile
import com.nubari.montra.ui.theme.*
import kotlinx.coroutines.launch

private val tabs = listOf(
    "Today",
    "Week",
    "Month",
    "Year"
)
private val dummyTx = listOf(
    Pair(Pair(yellow20, yellow100), R.drawable.shopping_bag),
    Pair(Pair(violet40, violet100), R.drawable.recurring_bill),
    Pair(Pair(red20, red100), R.drawable.restaurant),
    Pair(Pair(yellow20, yellow100), R.drawable.shopping_bag),
    Pair(Pair(violet40, violet100), R.drawable.recurring_bill),
    Pair(Pair(red20, red100), R.drawable.restaurant),
)

@Composable
fun RecentTransactions() {
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
        Column(
            modifier = Modifier
                .height(300.dp)
                .verticalScroll(rememberScrollState())
        ) {
            dummyTx.forEach {
                TransactionTile(
                    colorPair = it.first,
                    icon = it.second
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

    }
}

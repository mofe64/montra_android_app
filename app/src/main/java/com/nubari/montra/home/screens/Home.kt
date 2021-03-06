package com.nubari.montra.home.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nubari.montra.R
import com.nubari.montra.general.components.app.MainAppBar
import com.nubari.montra.general.components.input.DropDown
import com.nubari.montra.general.util.Util.months
import com.nubari.montra.home.components.HomeBanner
import com.nubari.montra.home.components.RecentTransactions
import com.nubari.montra.home.components.SpendingFrequency
import com.nubari.montra.home.events.HomeEvent
import com.nubari.montra.home.viewmodels.HomeViewModel
import com.nubari.montra.ui.theme.*
import java.util.*


@ExperimentalPagerApi
@Composable
fun Home(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val state = homeViewModel.state.value


    val calender = Calendar.getInstance(Locale.getDefault())
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainAppBar(
                navController = navController,
                title = {
                    DropDown(
                        modifier = Modifier.fillMaxWidth(),
                        updateValue = {
                            homeViewModel.createEvent(HomeEvent.ChangeMonth(it))
                        },
                        options = months,
                        startingIndex = calender.get(Calendar.MONTH)
                    )
                },
                backgroundColor = oldLace
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.notifiaction),
                        contentDescription = "notifications",
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HomeBanner(
                accountBalance = state
                    .account?.balance?.toPlainString() ?: "0",
                income = state.income,
                expenses = state.expenses
            )
            SpendingFrequency(
                spendingData = state.spendingData ?: emptyList()
            )
            RecentTransactions(
                recentTx = state.recentTransactions ?: emptyList(),
                navController = navController
            )
        }
    }
}


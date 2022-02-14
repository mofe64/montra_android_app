package com.nubari.montra.home.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nubari.montra.R
import com.nubari.montra.general.components.*
import com.nubari.montra.home.components.HomeBanner
import com.nubari.montra.home.components.RecentTransactions
import com.nubari.montra.home.components.SpendingFrequency
import com.nubari.montra.ui.theme.*


@ExperimentalPagerApi
@Composable
fun Home(
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainAppBar(
                navController = navController,
                title = {
                    MonthDropDown(
                        modifier = Modifier.fillMaxWidth(),
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
            HomeBanner()
            SpendingFrequency()
            RecentTransactions()
        }

    }
}


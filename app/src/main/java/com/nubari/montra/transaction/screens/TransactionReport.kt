package com.nubari.montra.transaction.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nubari.montra.general.components.app.MainAppBar

@Composable
fun TransactionReport(
    navController: NavController
) {
    val systemUiController: SystemUiController = rememberSystemUiController()
    LaunchedEffect(Unit) {
        systemUiController.isNavigationBarVisible = true
    }
    Scaffold(
        topBar = {
            MainAppBar(
                navController = navController,
                title = {
                    Text(
                        text = "Financial Report",
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
                .systemBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Financial Report")
        }
    }
}
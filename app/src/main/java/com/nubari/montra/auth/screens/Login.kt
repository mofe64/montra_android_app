package com.nubari.montra.auth.screens

import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.nubari.montra.general.components.MainAppBar

@Composable
fun Login(
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainAppBar(
                navController = navController,
                title = {
                    Text(
                        text = "Sign Up",
                    )
                },
                backIconColor = Color.Black
            )
        },

        )
    {

    }
}
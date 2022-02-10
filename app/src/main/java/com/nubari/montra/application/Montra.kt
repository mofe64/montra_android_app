package com.nubari.montra.application

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.rememberNavController
import com.nubari.montra.navigation.navhosts.NavigationHost

@ExperimentalComposeUiApi
@Composable
fun Montra() {
    val navController = rememberNavController()
    Scaffold() {
        NavigationHost(navController = navController)
    }
}
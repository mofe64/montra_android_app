package com.nubari.montra.application

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.nubari.montra.navigation.navhosts.NavigationHost

@Composable
fun Montra() {
    val navController = rememberNavController()
    Scaffold() {
        NavigationHost(navController = navController)
    }
}
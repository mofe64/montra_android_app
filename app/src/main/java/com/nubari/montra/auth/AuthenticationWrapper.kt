package com.nubari.montra.auth

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nubari.montra.auth.viewmodels.AuthViewModel
import com.nubari.montra.navigation.navhosts.AuthenticationNavigationHost

@ExperimentalPagerApi
@Composable
fun AuthenticationWrapper(
    viewModel: AuthViewModel
) {
    val navController = rememberNavController()
    AuthenticationNavigationHost(navController = navController)

}
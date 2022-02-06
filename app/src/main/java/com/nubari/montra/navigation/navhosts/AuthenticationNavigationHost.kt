package com.nubari.montra.navigation.navhosts

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nubari.montra.auth.screens.Login
import com.nubari.montra.auth.screens.SignUp
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.welcome.screens.onboarding.OnBoarding

@ExperimentalPagerApi
@Composable
fun AuthenticationNavigationHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Destination.OnBoardingDestination.route
    ) {
        composable(Destination.OnBoardingDestination.route) {
            OnBoarding(navController = navController)
        }
        composable(Destination.SignupDestination.route) {
            SignUp(navController = navController)
        }
        composable(Destination.LoginDestination.route) {
            Login(navController = navController)
        }

    }
}
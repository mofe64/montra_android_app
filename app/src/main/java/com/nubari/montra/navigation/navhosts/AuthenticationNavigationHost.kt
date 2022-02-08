package com.nubari.montra.navigation.navhosts

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nubari.montra.auth.screens.Login
import com.nubari.montra.auth.screens.SignUp
import com.nubari.montra.auth.screens.Verification
import com.nubari.montra.auth.viewmodels.AuthViewModel
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.welcome.screens.onboarding.OnBoarding

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@Composable
fun AuthenticationNavigationHost(
    navController: NavHostController
) {
    //TODO("REPLACE WITH HILT INJECTION")
    val authViewModel: AuthViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Destination.OnBoardingDestination.route
    ) {
        composable(Destination.OnBoardingDestination.route) {
            OnBoarding(navController = navController)
        }
        composable(Destination.SignupDestination.route) {
            SignUp(navController = navController, authViewModel = authViewModel)
        }
        composable(Destination.LoginDestination.route) {
            Login(navController = navController)
        }
        composable(Destination.VerificationDestination.route) {
            Verification(navController = navController, authViewModel = authViewModel)
        }

    }
}
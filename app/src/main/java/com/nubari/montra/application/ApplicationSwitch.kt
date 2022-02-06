package com.nubari.montra.application

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nubari.montra.auth.AuthenticationWrapper
import com.nubari.montra.auth.viewmodels.AuthViewModel

@ExperimentalPagerApi
@Composable
fun ApplicationSwitch(
    authViewModel: AuthViewModel = viewModel()
) {
    val authState = authViewModel.state.value

    if (authState.isAuthenticated) {
        print("hello world")
    } else {
        AuthenticationWrapper(viewModel = authViewModel)
    }
}
package com.nubari.montra.application

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nubari.montra.auth.AuthenticationWrapper
import com.nubari.montra.auth.viewmodels.AuthViewModel
import com.nubari.montra.navigation.navhosts.NavigationHost
import com.nubari.montra.preferences

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@Composable
fun ApplicationSwitch(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val authState = authViewModel.state.value
    if (authState.isAuthenticated) {
        Montra()
    } else {
        AuthenticationWrapper(viewModel = authViewModel)
    }
}
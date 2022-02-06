package com.nubari.montra.auth.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nubari.montra.auth.events.AuthEvent
import com.nubari.montra.auth.state.AuthState

class AuthViewModel : ViewModel() {
    private val _state = mutableStateOf(AuthState())
    val state: State<AuthState> = _state

    fun createEvent(event: AuthEvent) {}
    private fun onEvent(event: AuthEvent) {}
}
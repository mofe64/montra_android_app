package com.nubari.montra.auth.state

import com.nubari.montra.data.UserDetails

data class AuthState(
    val isAuthenticated: Boolean = false,
    var userDetails: UserDetails? = null,
    val inLoginMode: Boolean = true,
    val isProcessing: Boolean = false
)

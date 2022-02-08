package com.nubari.montra.auth.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nubari.montra.auth.events.AuthEvent
import com.nubari.montra.auth.state.AuthState
import com.nubari.montra.data.UserDetails
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _state = mutableStateOf(AuthState())
    val state: State<AuthState> = _state

    fun createEvent(event: AuthEvent) {
        onEvent(event)
    }

    private val _eventFlow = MutableSharedFlow<AuthEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Register -> {
                viewModelScope.launch {

                    _state.value = state.value.copy(
                        isProcessing = true,
                    )
                    /***
                     * At this point, make call to registration endpoint
                     * userDetails should be return value from that call
                     * Mocking that data for now
                     *
                     * Also need to implement error handling if call fails
                     *
                     * */
                    delay(3000)
                    val userDetails = UserDetails(event.name, event.email)
                    Log.i("testVerify2", userDetails.toString())
                    _state.value = state.value.copy(
                        isProcessing = false,
                        userDetails = userDetails
                    )
                    Log.i("testVerify3", state.value.toString())
                    _eventFlow.emit(
                        AuthEvent.SuccessfulRegistration("HELLO WORLD")
                    )
                }
            }
            is AuthEvent.Login -> {}
            is AuthEvent.VerifyEmail -> {
            }
            else -> {}
        }
    }
}
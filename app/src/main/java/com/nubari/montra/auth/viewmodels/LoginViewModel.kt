package com.nubari.montra.auth.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nubari.montra.auth.events.LoginEvent
import com.nubari.montra.auth.state.LoginFormState
import com.nubari.montra.auth.util.AuthUtil
import com.nubari.montra.general.util.InputType

class LoginViewModel : ViewModel() {
    private val _state = mutableStateOf(LoginFormState(formValid = true))
    val state: State<LoginFormState> = _state


    fun createEvent(event: LoginEvent) {
        onEvent(event)
    }

    private fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _state.value = state.value.copy(
                    email = state.value.email.copy(
                        text = event.value
                    )
                )
            }
            is LoginEvent.EnteredPassword -> {
                _state.value = state.value.copy(
                    password = state.value.password.copy(
                        text = event.value
                    )
                )
            }
            is LoginEvent.FocusChange -> {
                when (event.focusFieldName) {
                    "email" -> {
                        Log.i("validating", state.value.email.toString())
                        val validity: Pair<Boolean, String> =
                            AuthUtil.validateAuthInput(state.value.email.text, InputType.EMAIL)
                        _state.value = state.value.copy(
                            email = state.value.email.copy(
                                isValid = validity.first,
                                errorMessage = validity.second
                            ),
                            formValid = validity.first
                        )
                    }
                    "password" -> {
                        Log.i("validating", state.value.password.toString())
                        val validity: Pair<Boolean, String> = AuthUtil.validateAuthInput(
                            state.value.password.text,
                            InputType.PASSWORD
                        )
                        _state.value = state.value.copy(
                            password = state.value.password.copy(
                                isValid = validity.first,
                                errorMessage = validity.second
                            ),
                            formValid = validity.first
                        )
                    }
                }
            }
        }
    }
}
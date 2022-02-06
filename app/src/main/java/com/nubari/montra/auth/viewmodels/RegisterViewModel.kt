package com.nubari.montra.auth.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nubari.montra.auth.events.RegistrationEvent
import com.nubari.montra.auth.state.RegistrationFormState
import com.nubari.montra.auth.util.AuthUtil
import com.nubari.montra.general.util.InputType

class RegisterViewModel : ViewModel() {
    private val _state = mutableStateOf(RegistrationFormState(formValid = true))
    val state: State<RegistrationFormState> = _state


    fun createEvent(event: RegistrationEvent) {
        onEvent(event = event)
    }

    private fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.EnteredEmail -> {
                _state.value = state.value.copy(
                    email = state.value.email.copy(
                        text = event.value
                    )
                )
            }
            is RegistrationEvent.EnteredName -> {
                _state.value = state.value.copy(
                    name = state.value.name.copy(
                        text = event.value
                    )
                )
            }
            is RegistrationEvent.EnteredPassword -> {
                _state.value = state.value.copy(
                    password = state.value.password.copy(
                        text = event.value
                    )
                )
            }
            is RegistrationEvent.FocusChange -> {
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
                    "name" -> {
                        Log.i("validating", state.value.name.toString())
                        val validity: Pair<Boolean, String> =
                            AuthUtil.validateAuthInput(state.value.name.text, InputType.TEXT)
                        _state.value = state.value.copy(
                            name = state.value.name.copy(
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
            is RegistrationEvent.ToggleTermsCheckBox -> {
                _state.value = state.value.copy(
                    agreedToTerms = !state.value.agreedToTerms,
                    formValid = !state.value.agreedToTerms
                )
            }
        }
    }
}
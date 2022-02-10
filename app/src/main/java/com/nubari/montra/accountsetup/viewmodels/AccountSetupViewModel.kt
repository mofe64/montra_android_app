package com.nubari.montra.accountsetup.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nubari.montra.accountsetup.events.AccountFormEvent
import com.nubari.montra.accountsetup.state.AccountFormState
import com.nubari.montra.general.util.InputType
import com.nubari.montra.general.util.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountSetupViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(
        AccountFormState(formValid = true)
    )
    val state: State<AccountFormState> = _state

    fun createEvent(event: AccountFormEvent) {
        onEvent(event = event)
    }

    private fun onEvent(event: AccountFormEvent) {
        when (event) {
            is AccountFormEvent.EnteredName -> {
                _state.value = state.value.copy(
                    name = state.value.name.copy(
                        text = event.value
                    )
                )
            }
            is AccountFormEvent.EnteredInitialBalance -> {
                _state.value = state.value.copy(
                    initialBalance = state.value.initialBalance.copy(
                        text = event.value
                    )
                )
            }
            is AccountFormEvent.FocusChange -> {
                when (event.focusFieldName) {
                    "name" -> {
                        val validity =
                            Util.validateInput(
                                state.value.name.text, InputType.TEXT
                            )
                        _state.value = state.value.copy(
                            name = state.value.name.copy(
                                isValid = validity.first,
                                errorMessage = validity.second
                            ),
                            formValid = validity.first
                        )
                    }
                    "initialBalance" -> {}
                }
            }
        }
    }
}
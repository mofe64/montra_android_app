package com.nubari.montra.accountsetup.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nubari.montra.accountsetup.events.AccountFormEvent
import com.nubari.montra.accountsetup.events.AccountFormUIEvent
import com.nubari.montra.accountsetup.events.AccountProcessEvent
import com.nubari.montra.accountsetup.state.AccountFormState
import com.nubari.montra.data.remote.requests.AccountCreationRequest
import com.nubari.montra.domain.usecases.account.AccountUseCases
import com.nubari.montra.general.util.InputType
import com.nubari.montra.general.util.Resource
import com.nubari.montra.general.util.Util
import com.nubari.montra.preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AccountSetupViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases
) : ViewModel() {
    private val _state = mutableStateOf(
        AccountFormState(formValid = true)
    )
    val state: State<AccountFormState> = _state

    private val _eventFlow = MutableSharedFlow<AccountFormEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun createEvent(event: AccountFormEvent) {
        onEvent(event = event)
    }

    private fun onEvent(event: AccountFormEvent) {
        when (event) {
            is AccountFormUIEvent.EnteredName -> {
                _state.value = state.value.copy(
                    name = state.value.name.copy(
                        text = event.value
                    )
                )
            }
            is AccountFormUIEvent.EnteredInitialBalance -> {
                _state.value = state.value.copy(
                    initialBalance = state.value.initialBalance.copy(
                        text = event.value
                    )
                )
            }
            is AccountFormUIEvent.FocusChange -> {
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
            is AccountFormUIEvent.Create -> {
                val request = AccountCreationRequest(
                    name = event.name,
                    balance = Util.cleanNumberInput(event.balance),
                    userId = preferences.userId
                )
                createAccount(request = request)
            }
            else -> {}
        }
    }

    private fun createAccount(request: AccountCreationRequest) {
        accountUseCases.createAccount(request = request)
            .onEach {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isProcessing = true
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isProcessing = false,
                            errorOccurred = true,
                            errorMessage = it.message
                                ?: "An unexpected error occurred, Please try again later"
                        )
                        _eventFlow.emit(
                            AccountProcessEvent.FailedAccountCreation(
                                errorMessage = state.value.errorMessage
                            )
                        )
                    }
                    is Resource.Success -> {
                        _eventFlow.emit(
                            AccountProcessEvent.SuccessfulAccountCreation
                        )
                    }

                }
            }.launchIn(viewModelScope)
    }
}


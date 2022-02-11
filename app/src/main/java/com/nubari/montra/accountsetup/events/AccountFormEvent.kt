package com.nubari.montra.accountsetup.events

sealed class AccountFormEvent {}

sealed class AccountFormUIEvent : AccountFormEvent() {
    data class EnteredName(val value: String) : AccountFormUIEvent()
    data class EnteredInitialBalance(val value: String) : AccountFormUIEvent()
    data class FocusChange(val focusFieldName: String) : AccountFormUIEvent()
    data class Create(val name: String, val balance: String) : AccountFormUIEvent()
}

sealed class AccountProcessEvent : AccountFormEvent() {
    object SuccessfulAccountCreation : AccountProcessEvent()
    data class FailedAccountCreation(
        val errorMessage: String
    ) : AccountProcessEvent()
}

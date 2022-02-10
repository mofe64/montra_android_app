package com.nubari.montra.accountsetup.events

sealed class AccountFormEvent {
    data class EnteredName(val value: String) : AccountFormEvent()
    data class EnteredInitialBalance(val value: String) : AccountFormEvent()
    data class FocusChange(val focusFieldName: String) : AccountFormEvent()
}

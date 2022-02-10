package com.nubari.montra.auth.events

sealed class LoginEvent {
    data class EnteredEmail(val value: String) : LoginEvent()
    data class EnteredPassword(val value: String) : LoginEvent()
    data class FocusChange(val focusFieldName: String) : LoginEvent()
}

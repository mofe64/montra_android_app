package com.nubari.montra.auth.events

sealed class RegistrationEvent {
    data class EnteredEmail(val value: String) : RegistrationEvent()
    data class EnteredName(val value: String) : RegistrationEvent()
    data class EnteredPassword(val value: String) : RegistrationEvent()
    data class FocusChange(val focusFieldName: String) : RegistrationEvent()
}

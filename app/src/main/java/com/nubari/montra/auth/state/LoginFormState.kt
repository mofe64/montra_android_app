package com.nubari.montra.auth.state

import com.nubari.montra.general.state.InputState
import com.nubari.montra.general.util.InputType

data class LoginFormState(
    val email : InputState = InputState(type = InputType.EMAIL),
    val password: InputState = InputState(type = InputType.PASSWORD),
    val formValid: Boolean
)
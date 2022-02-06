package com.nubari.montra.general.state

import com.nubari.montra.general.util.InputType

data class InputState(
    val text: String = "",
    val isValid: Boolean = true,
    val type: InputType,
    val errorMessage: String = ""
)
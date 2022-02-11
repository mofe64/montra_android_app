package com.nubari.montra.accountsetup.state

import com.nubari.montra.general.state.InputState
import com.nubari.montra.general.util.InputType

data class AccountFormState(
    val name: InputState = InputState(type = InputType.TEXT),
    val initialBalance: InputState = InputState(type = InputType.NUMBER),
    val formValid: Boolean,
    val isProcessing: Boolean = false,
    val errorOccurred: Boolean = true,
    val errorMessage: String = ""

)

package com.nubari.montra.auth.util

import android.text.TextUtils
import com.nubari.montra.general.util.InputType

object AuthUtil {
    fun validateAuthInput(inputValue: String, inputType: InputType): Pair<Boolean, String> {
        when (inputType) {
            InputType.EMAIL -> {
                val emailValid =
                    !TextUtils.isEmpty(inputValue) && android.util.Patterns.EMAIL_ADDRESS.matcher(
                        inputValue
                    ).matches()
                return if (emailValid) {
                    Pair(emailValid, "")
                } else {
                    Pair(emailValid, "Invalid Email Provided")
                }

            }
            InputType.PASSWORD -> {
                val passwordValid = !TextUtils.isEmpty(inputValue) && inputValue.length > 5
                return if (passwordValid) {
                    Pair(passwordValid, "")
                } else {
                    Pair(passwordValid, "Password Can't be less that 5 characters")
                }
            }
            InputType.TEXT -> {
                val textValid = !TextUtils.isEmpty(inputValue)
                return if (textValid) {
                    Pair(textValid, "")
                } else {
                    Pair(textValid, "Text cannot be empty")
                }
            }

        }

    }
}
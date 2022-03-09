package com.nubari.montra.general.util

import android.text.TextUtils
import com.nubari.montra.R
import com.nubari.montra.general.util.InputType
import com.nubari.montra.ui.theme.*
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

object Util {
    fun validateInput(inputValue: String, inputType: InputType): Pair<Boolean, String> {
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
            InputType.NUMBER -> {
                if (inputValue.toBigDecimal().toDouble() <= 0.0) {
                    return Pair(false, "Value cannot be less than 0.1")
                }
                return Pair(true, "")
            }

        }

    }

    fun cleanNumberInput(inputValue: String): String {
        val numberParts = inputValue.split(",")
        return numberParts.joinToString("")
    }

    val iconMap = mapOf(
        Pair("Transport", R.drawable.car),
        Pair("Food", R.drawable.restaurant),
        Pair("Bills", R.drawable.recurring_bill),
        Pair("Salary", R.drawable.salary),
        Pair("Shopping", R.drawable.shopping_bag),
        Pair("Entertainment", R.drawable.movie_open_settings),
        Pair("Health", R.drawable.hospital_building),
        Pair("Investment", R.drawable.chart_areaspline),
        Pair("Groceries", R.drawable.bread_slice),
        Pair("Travel", R.drawable.airplane),
        Pair("Love", R.drawable.heart),
        Pair("Drinks", R.drawable.glass_cocktail),
        Pair("General", R.drawable.rocket_launch),
    )

    val txColors = listOf(
        Pair(yellow20, yellow100),
        Pair(violet20, violet100),
        Pair(red20, red100),
        Pair(blue20, blue100),
        Pair(green20, green100)
    )
}

fun BigDecimal.setPrecision(newPrecision: Int) = BigDecimal(toPlainString(), MathContext(newPrecision,RoundingMode.HALF_UP))
package com.nubari.montra.welcome.screens.onboarding

import androidx.annotation.DrawableRes
import com.nubari.montra.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val onBoardingPages = listOf(
    Page(
        title = "Gain total control of your money",
        description = "Become your own money manager and make every cent count",
        image = R.drawable.onboardillustration3
    ),
    Page(
        title = "Know where your money goes",
        description = "Track your transaction easily with categories and financial reports",
        image = R.drawable.onboardillustration1
    ),
    Page(
        title = "Planning ahead",
        description = "Setup your budget for each category so you stay in control",
        image = R.drawable.onboardillustration2_svg
    )


)

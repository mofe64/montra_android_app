package com.nubari.montra.navigation.destinations

import com.nubari.montra.R

sealed class PrimaryDestination(
    val name: String,
    val startRoute: String,
    val rootRoute: String,
    val icon: Int
) {
    object AccountSetup : PrimaryDestination(
        name = "account setup",
        startRoute = "account_setup_prompt",
        rootRoute = "account_setup_root",
        icon = 0
    )

    object Home : PrimaryDestination(
        name = "home",
        startRoute = Destination.Home.route,
        rootRoute = "home_root",
        icon = R.drawable.home
    )

    object Transaction : PrimaryDestination(
        name = "transaction",
        startRoute = Destination.Transactions.route,
        rootRoute = "transaction_root",
        icon = R.drawable.transaction
    )

    object Budget : PrimaryDestination(
        name = "budget",
        startRoute = Destination.Budget.route,
        rootRoute = "budget_root",
        icon = R.drawable.pie_chart
    )

    object Profile : PrimaryDestination(
        name = "profile",
        startRoute = Destination.Profile.route,
        rootRoute = "profile_root",
        icon = R.drawable.user
    )

    object Empty : PrimaryDestination(
        name = "",
        startRoute = "",
        rootRoute = "",
        icon = -1
    )
}

val primaryDestinations = listOf(
    PrimaryDestination.Home,
    PrimaryDestination.Transaction,
    PrimaryDestination.Empty,
    PrimaryDestination.Budget,
    PrimaryDestination.Profile
)

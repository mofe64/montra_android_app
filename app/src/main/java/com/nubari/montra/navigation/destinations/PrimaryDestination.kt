package com.nubari.montra.navigation.destinations

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
        startRoute = "home",
        rootRoute = "home_root",
        icon = 1
    )
}

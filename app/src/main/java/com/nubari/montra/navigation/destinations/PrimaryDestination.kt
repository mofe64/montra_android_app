package com.nubari.montra.navigation.destinations

sealed class PrimaryDestination(
    val name: String,
    val route: String,
    val root: String,
    val icon: Int
) {
    object Home : PrimaryDestination(
        name = "home",
        route = "home",
        root = "home_root",
        icon = 1
    )
}

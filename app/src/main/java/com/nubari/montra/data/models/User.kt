package com.nubari.montra.data.models

data class UserDetails(
    val name: String,
    val email: String,
    val id: String,
    val token: String = ""
)
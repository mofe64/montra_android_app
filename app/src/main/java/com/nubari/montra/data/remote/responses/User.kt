package com.nubari.montra.data.remote.responses

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    val id: String,
    val email: String,
    val name: String,
    val verified: Boolean
)
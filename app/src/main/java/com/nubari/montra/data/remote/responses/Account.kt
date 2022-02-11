package com.nubari.montra.data.remote.responses

import com.google.gson.annotations.SerializedName

data class Account(
    @SerializedName("_id")
    val id: String,
    val balance: String,
    val name: String,
    val owner: User
)
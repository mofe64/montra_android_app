package com.nubari.montra.home.events

sealed class HomeEvent {
    data class ChangeMonth(val newMonth: Int) : HomeEvent()
}

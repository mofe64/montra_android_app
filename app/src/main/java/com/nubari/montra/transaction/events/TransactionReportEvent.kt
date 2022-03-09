package com.nubari.montra.transaction.events

sealed class TransactionReportEvent {
    data class SwitchedActiveTab(val newTab: String): TransactionReportEvent()
    data class SwitchedActiveTabView(val newView : String) : TransactionReportEvent()
    data class ChangeActiveGraph(val newGraph: String) : TransactionReportEvent()
    data class ChangeSortDirection(val newDirection: String) : TransactionReportEvent()
}

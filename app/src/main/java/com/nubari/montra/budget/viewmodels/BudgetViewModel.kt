package com.nubari.montra.budget.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nubari.montra.budget.events.BudgetsEvent
import com.nubari.montra.budget.state.AllBudgetsState
import com.nubari.montra.data.local.models.Budget
import com.nubari.montra.domain.usecases.budget.BudgetUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetUseCases: BudgetUseCases
) : ViewModel() {
    private val _state = mutableStateOf(
        AllBudgetsState()
    )
    val state: State<AllBudgetsState> = _state
    private var loadBudgetsJob: Job? = null

    init {
        loadBudgets()
    }

    fun createEvent(event: BudgetsEvent) {
        onEvent(event)
    }

    private fun onEvent(event: BudgetsEvent) {
        when (event) {
            is BudgetsEvent.ChangeMonth -> {
                _state.value = state.value.copy(
                    currentMonth = event.newMonth,
                    monthsBudgets = filterBudgetsByMonth(
                        state.value.budgets, event.newMonth
                    )
                )
            }
        }
    }

    private fun loadBudgets() {
        //TODO this should be scoped to an account id not all budgets in db
        loadBudgetsJob?.cancel()
        loadBudgetsJob = budgetUseCases
            .getBudgets()
            .onEach {
                _state.value = state.value.copy(
                    budgets = it,
                    monthsBudgets = filterBudgetsByMonth(it, state.value.currentMonth)
                )
            }
            .launchIn(viewModelScope)
    }


    private fun filterBudgetsByMonth(budgets: List<Budget>, monthIndex: Int): List<Budget> {
        return budgets.filter {
            it.startDate.month == monthIndex
        }
    }
}
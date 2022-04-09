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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetUseCases: BudgetUseCases
) : ViewModel() {
    private val tag = "ALL-BUDGETS-VIEWMODEL"
    private val _state = mutableStateOf(
        AllBudgetsState()
    )
    val state: State<AllBudgetsState> = _state
    private var loadBudgetsJob: Job? = null

    init {
        Timber.tag(tag).i("init block called")
        loadBudgets()
    }


    private fun loadBudgets() {
        //TODO this should be scoped to an account id not all budgets in db
        loadBudgetsJob?.cancel()
        loadBudgetsJob = budgetUseCases
            .getBudgets()
            .onEach {
                _state.value = state.value.copy(
                    budgets = it,
                )
            }
            .launchIn(viewModelScope)
    }

}
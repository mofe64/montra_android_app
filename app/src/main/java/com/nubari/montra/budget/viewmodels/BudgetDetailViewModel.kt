package com.nubari.montra.budget.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nubari.montra.budget.events.BudgetDetailEvent
import com.nubari.montra.budget.events.BudgetProcessEvent
import com.nubari.montra.budget.state.BudgetDetailState
import com.nubari.montra.domain.usecases.budget.BudgetUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetDetailViewModel @Inject constructor(
    private val budgetUseCases: BudgetUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(
        BudgetDetailState(
            budget = null
        )
    )
    val state: State<BudgetDetailState> = _state

    private val _eventFlow = MutableSharedFlow<BudgetProcessEvent>()
    val eventFLow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("bdId")?.let {
            Log.i("yyy", "id is $it")
            if (it.isNotEmpty()) {
                viewModelScope.launch {
                    budgetUseCases
                        .getBudgetByID(id = it)
                        ?.also { bd ->
                            Log.i("yyy", bd.toString())
                            _state.value = state.value.copy(
                                budget = bd
                            )
                        }
                }
            }
        }
    }

    fun createEvent(event: BudgetDetailEvent) {
        onEvent(event)
    }

    private fun onEvent(event: BudgetDetailEvent) {
        when (event) {
            is BudgetDetailEvent.DeleteBudget -> {
                viewModelScope.launch {
                    budgetUseCases.deleteBudget(event.bd)
                    _eventFlow.emit(
                        BudgetProcessEvent.BudgetDeleteSuccess
                    )
                }
            }
        }
    }
}
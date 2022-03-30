package com.nubari.montra.budget.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nubari.montra.budget.events.BudgetFormEvent
import com.nubari.montra.budget.events.BudgetProcessEvent
import com.nubari.montra.budget.state.BudgetFormState
import com.nubari.montra.data.local.models.Budget
import com.nubari.montra.data.local.models.Category
import com.nubari.montra.data.local.models.enums.BudgetType
import com.nubari.montra.domain.exceptions.BudgetException
import com.nubari.montra.domain.usecases.budget.BudgetUseCases
import com.nubari.montra.domain.usecases.category.CategoryUseCases
import com.nubari.montra.general.util.InputType
import com.nubari.montra.general.util.Util
import com.nubari.montra.preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject
import kotlin.NoSuchElementException

@HiltViewModel
class BudgetFormViewModel @Inject constructor(
    private val budgetUseCases: BudgetUseCases,
    categoryUseCases: CategoryUseCases
) : ViewModel() {
    private val _state = mutableStateOf(
        BudgetFormState()
    )
    val state: State<BudgetFormState> = _state
    private val _eventFlow = MutableSharedFlow<BudgetProcessEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        categoryUseCases.getAllCategories()
            .onEach {
                _state.value = state.value.copy(
                    categories = it
                )
            }.launchIn(viewModelScope)
    }

    fun createEvent(event: BudgetFormEvent) {
        onEvent(event)
    }

    private fun onEvent(event: BudgetFormEvent) {
        when (event) {
            is BudgetFormEvent.ChangedCategory -> {
                _state.value = state.value.copy(
                    category = state.value.category.copy(
                        text = event.category
                    )
                )
            }
            is BudgetFormEvent.ChangeBudgetType -> {
                _state.value = state.value.copy(
                    budgetType = event.budgetType
                )
            }
            is BudgetFormEvent.EnteredAmount -> {
                _state.value = state.value.copy(
                    limit = state.value.limit.copy(
                        text = event.amount
                    )
                )
            }
            is BudgetFormEvent.SetThreshold -> {
                _state.value = state.value.copy(
                    threshold = event.thresholdRaw
                )
            }
            is BudgetFormEvent.ToggleReminder -> {
                _state.value = state.value.copy(
                    shouldAlert = !state.value.shouldAlert
                )
            }
            is BudgetFormEvent.CreateBudget -> {
                val formIsValid = validateForm()
                if (!formIsValid) {
                    return
                }
                var category = Category("", "", "")
                try {
                    category = state.value.categories.first {
                        it.name == state.value.category.text
                    }
                } catch (e: NoSuchElementException) {
                    if (state.value.budgetType == BudgetType.CATEGORY) {
                        viewModelScope.launch {
                            _eventFlow.emit(
                                BudgetProcessEvent.BudgetCreationFail("Select a Valid Category")
                            )
                        }
                        return
                    }

                }

                _state.value = state.value.copy(
                    isProcessing = true
                )
                val cleanedLimit = Util.cleanNumberInput(event.amount)
                val thresholdPercentage = (event.thresholdRaw * 100).toInt()
                viewModelScope.launch {
                    val budget = Budget(
                        id = UUID.randomUUID().toString(),
                        budgetType = event.budgetType,
                        shouldNotify = event.shouldRemind,
                        threshold = thresholdPercentage,
                        exceeded = false,
                        startDate = Date(),
                        limit = BigDecimal(cleanedLimit),
                        spend = BigDecimal.ZERO,
                        categoryId = if (event.budgetType == BudgetType.CATEGORY) {
                            category.id
                        } else {
                            null
                        },
                        categoryName = if (event.budgetType == BudgetType.CATEGORY) {
                            category.name
                        } else {
                            "General"
                        },
                        accountId = preferences.activeAccountId
                    )
                    try {
                        budgetUseCases.createBudget(budget)
                        _state.value = state.value.copy(
                            isProcessing = false
                        )
                        _eventFlow.emit(
                            BudgetProcessEvent.BudgetCreationSuccess
                        )
                    } catch (e: BudgetException) {
                        _eventFlow.emit(
                            BudgetProcessEvent.BudgetCreationFail(
                                message = e.localizedMessage ?: "Could not save budget"
                            )
                        )
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            BudgetProcessEvent.BudgetCreationFail(
                                message = e.localizedMessage
                                    ?: "Could not save budget, please try again"
                            )
                        )
                    }

                }

            }
            is BudgetFormEvent.FocusChange -> {
                when (event.fieldName) {
                    "limit" -> {
                        val limit = state.value.limit.text
                        val cleanedLimit = Util.cleanNumberInput(limit)
                        val validity = Util.validateInput(
                            cleanedLimit, InputType.NUMBER
                        )
                        _state.value = state.value.copy(
                            limit = state.value.limit.copy(
                                isValid = validity.first,
                                errorMessage = validity.second
                            ),
                            formValid = validity.first
                        )
                    }
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        var formValid = true
        val selectedCategory = state.value.category.text
        val validCategories = state.value.categories
        val match = validCategories.filter {
            it.name == selectedCategory
        }
        if (match.isEmpty() && state.value.budgetType == BudgetType.CATEGORY) {
            formValid = false
            _state.value = state.value.copy(
                formValid = false,
                category = state.value.category.copy(
                    isValid = false,
                    errorMessage = "Please select a valid category"
                )
            )
        }
        val limit = state.value.limit.text
        val cleanedLimit = Util.cleanNumberInput(limit)
        val limitValidity = Util.validateInput(
            cleanedLimit, InputType.NUMBER
        )
        if (!limitValidity.first) {
            formValid = false
            _state.value = state.value.copy(
                limit = state.value.limit.copy(
                    isValid = limitValidity.first,
                    errorMessage = limitValidity.second
                ),
                formValid = limitValidity.first
            )
        }


        return formValid
    }
}
package com.nubari.montra.home.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nubari.montra.domain.usecases.account.AccountUseCases
import com.nubari.montra.home.state.HomeState
import com.nubari.montra.preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases
) : ViewModel() {
    private val _state = mutableStateOf(
        HomeState(
            account = null
        )
    )
    val state: State<HomeState> = _state

    init {
        val activeAccountId = preferences.activeAccountId
        if (activeAccountId.isEmpty()) {
            accountUseCases.getAllAccounts()
                .onEach {
                    _state.value = state.value.copy(
                        account = it[0]
                    )
                    preferences.activeAccountId = it[0].id
                }.launchIn(viewModelScope)
        } else {
            viewModelScope.launch {
                val activeAccount = accountUseCases.getAccount(activeAccountId)
                _state.value = state.value.copy(
                    account = activeAccount
                )
            }
        }
        test()
    }

    private fun test() {
        val activeAccountId = preferences.activeAccountId
        accountUseCases.getAccountTransactions(accountId = activeAccountId)
            .onEach {
                Log.i("account-tx", it.toString())
            }.launchIn(viewModelScope)
    }

}


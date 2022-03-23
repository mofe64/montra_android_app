package com.nubari.montra.budget.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nubari.montra.data.local.models.Budget

@ExperimentalMaterialApi
@Composable
fun BudgetSection(
    budgetList: List<Budget>,
    toDetailFunc: (String) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(budgetList) { budget ->
            BudgetTile(
                budget = budget,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.4f),
                toDetail = {
                    toDetailFunc(it)
                }
            )
        }
    }
}
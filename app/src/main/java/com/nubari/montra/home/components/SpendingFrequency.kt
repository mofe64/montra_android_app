package com.nubari.montra.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val dummyData = listOf(
    700.0,
    450.0,
    1400.0,
    0.0,
    600.0,
)

@Composable
fun SpendingFrequency() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Spend Frequency",
            fontSize = 18.sp,
            fontWeight = FontWeight(600),
            modifier = Modifier.padding(
                start = 10.dp,
                end = 10.dp
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            SpendingFrequencyCanvas(
                data = dummyData,
                modifier = Modifier.height(120.dp)
            )
        }
    }
}
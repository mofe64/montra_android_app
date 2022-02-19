package com.nubari.montra.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SpendingFrequency(
    spendingData: List<Double>
) {
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
                data = spendingData,
                modifier = Modifier.height(120.dp)
            )
        }
    }
}
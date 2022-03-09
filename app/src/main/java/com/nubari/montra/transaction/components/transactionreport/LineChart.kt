package com.nubari.montra.transaction.components.transactionreport

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nubari.montra.home.components.SpendingFrequency

@Composable
fun LineChart(
    data: List<Double>,
    totalValue: String,
    pathColor: Color,
    fillColor: Color
) {
    Text(
        text = "₦ $totalValue",
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 10.dp)
    )
    SpendingFrequency(
        spendingData = data,
        shouldDisplayHeader = false,
        pathColor = pathColor,
        fillColor = fillColor
    )
}

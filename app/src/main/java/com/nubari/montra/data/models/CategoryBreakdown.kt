package com.nubari.montra.data.models


import com.nubari.montra.data.local.models.enums.TransactionType
import java.math.BigDecimal

data class CategoryBreakdown(
    var categoryName: String = "",
    var categoryPercentage: Float = 0f,
    var categoryAmount: BigDecimal = BigDecimal.ZERO,
    var txType : TransactionType = TransactionType.EXPENSE
)

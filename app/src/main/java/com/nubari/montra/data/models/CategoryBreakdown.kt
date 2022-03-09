package com.nubari.montra.data.models


import com.nubari.montra.data.local.models.enums.TransactionType
import java.math.BigDecimal

data class CategoryBreakdown(
    var categoryName: String = "",
    var categoryPercentage: Float = 0f,
    var categoryAmount: BigDecimal = BigDecimal.ZERO,
    var txType : TransactionType = TransactionType.EXPENSE
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CategoryBreakdown

        if (categoryName != other.categoryName) return false
        if (categoryPercentage != other.categoryPercentage) return false
        if (categoryAmount != other.categoryAmount) return false
        if (txType != other.txType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = categoryName.hashCode()
        result = 31 * result + categoryPercentage.hashCode()
        result = 31 * result + categoryAmount.hashCode()
        result = 31 * result + txType.hashCode()
        return result
    }
}

package com.nubari.montra.data.local.typeconverters

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.util.*

class ApplicationTypeConverters {
    @TypeConverter
    fun dateFromTypeStamp(value: Long?): Date {
        return value?.let { Date(it) } ?: Date()
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long {
        return date?.time?.toLong() ?: -1L
    }

    @TypeConverter
    fun bigDecimalToString(input: BigDecimal?): String {
        return input?.toPlainString() ?: ""
    }

    @TypeConverter
    fun stringToBigDecimal(input: String?): BigDecimal {
        if (input.isNullOrBlank()) return BigDecimal.valueOf(0.0)
        return input.toBigDecimalOrNull() ?: BigDecimal.valueOf(0.0)

    }
}
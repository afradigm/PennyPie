package com.afra.pennypie.data.local.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class DateTimeConverters {
    private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    private val yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM")

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        return value?.format(dateTimeFormatter)
    }

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it, dateTimeFormatter) }
    }

    @TypeConverter
    fun fromYearMonth(value: YearMonth?): String? {
        return value?.format(yearMonthFormatter)
    }

    @TypeConverter
    fun toYearMonth(value: String?): YearMonth? {
        return value?.let { YearMonth.parse(it, yearMonthFormatter) }
    }
} 
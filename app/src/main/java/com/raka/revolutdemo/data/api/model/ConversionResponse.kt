package com.raka.revolutdemo.data.api.model

import androidx.annotation.Keep
import java.math.BigDecimal
import java.util.*

@Keep
data class ConversionResponse (
    val base: String,
    val date: String,
    val rates: SortedMap<String, BigDecimal>
)
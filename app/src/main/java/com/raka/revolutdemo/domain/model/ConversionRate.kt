package com.raka.revolutdemo.domain.model

import androidx.annotation.Keep
import java.math.BigDecimal

@Keep
data class ConversionRate (
    val base: String,
    val date: String,
    val rates: List<Rate>
)

@Keep
data class Rate (
    var currency: String,
    var rate: BigDecimal
)
package com.raka.revolutdemo.data.cache.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.raka.revolutdemo.data.cache.converter.BigDecimalConverter
import java.math.BigDecimal

@TypeConverters(BigDecimalConverter::class)
@Entity
@Keep
data class ConversionCache (
    @PrimaryKey
    var currency: String,
    var rate: BigDecimal
)
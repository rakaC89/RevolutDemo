package com.raka.revolutdemo.data.cache.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

object BigDecimalConverter {

    @JvmStatic
    @TypeConverter
    fun bigDecimalToDouble(bigDecimal: BigDecimal): Double {
        return bigDecimal.toDouble()
    }

    @JvmStatic
    @TypeConverter
    fun doubleToBigDecimal(double: Double): BigDecimal {
        return double.toBigDecimal()
    }

}
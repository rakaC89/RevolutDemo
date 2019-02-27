package com.raka.revolutdemo.data.api.mapper

import com.raka.revolutdemo.data.api.model.ConversionResponse
import com.raka.revolutdemo.domain.mapper.Mapper
import com.raka.revolutdemo.domain.model.ConversionRate
import com.raka.revolutdemo.domain.model.Rate
import java.math.BigDecimal

object ApiConversionMapper : Mapper<ConversionResponse, ConversionRate>() {

    override fun toDomain(value: ConversionResponse): ConversionRate {
        return ConversionRate(
            base = value.base,
            date = value.date,
            rates = value.rates.map { rate -> mapRates(rate) }
        )
    }

    private fun mapRates(entry: Map.Entry<String, BigDecimal>): Rate {
        return Rate(
            currency = entry.key,
            rate = entry.value
        )
    }
}
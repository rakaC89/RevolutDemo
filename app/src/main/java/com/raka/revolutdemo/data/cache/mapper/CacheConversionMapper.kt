package com.raka.revolutdemo.data.cache.mapper

import com.raka.revolutdemo.data.cache.model.ConversionCache
import com.raka.revolutdemo.domain.mapper.ReversibleMapper
import com.raka.revolutdemo.domain.model.ConversionRate
import com.raka.revolutdemo.domain.model.Rate
import java.math.BigDecimal

object CacheConversionMapper : ReversibleMapper<List<ConversionCache>, ConversionRate>() {

    override fun toDomain(value: List<ConversionCache>): ConversionRate {
        return ConversionRate(
            base = "",
            date = "",
            rates = value.asIterable().map { Rate(currency = it.currency, rate = it.rate) }.toList()
        )
    }

    override fun fromDomain(value: ConversionRate): List<ConversionCache> {
        return listOf(ConversionCache(value.base, BigDecimal.ONE)) +
                value.rates.asIterable().map { ConversionCache(currency = it.currency, rate = it.rate) }.toList()
    }
}
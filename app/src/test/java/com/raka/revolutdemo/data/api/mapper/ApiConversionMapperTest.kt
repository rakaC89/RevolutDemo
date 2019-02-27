package com.raka.revolutdemo.data.api.mapper

import com.raka.revolutdemo.data.api.model.ConversionResponse
import com.raka.revolutdemo.domain.model.ConversionRate
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class ApiConversionMapperTest {

    companion object {
        const val BASE = "EUR"
        const val DATE = "2018-09-06"
        val rates =
            mapOf(Pair("AUD", BigDecimal(1.6213)), Pair("GBP", BigDecimal(0.90095)),
                Pair("INR", BigDecimal(83.97)), Pair("CAD", BigDecimal(1.5384))).toSortedMap()
    }
    lateinit var conversionRate: ConversionRate

    @Before
    fun init() {
        val conversionResponse = ConversionResponse(
            BASE,
            DATE,
            rates
        )

        conversionRate = ApiConversionMapper.toDomain(conversionResponse)
    }

    @Test
    fun `mapped base is valid`() {
        Assert.assertEquals(BASE, conversionRate.base)
    }

    @Test
    fun `mapped date is valid`() {
        Assert.assertEquals(DATE, conversionRate.date)
    }

    @Test
    fun `mapped rates are valid`() {
        Assert.assertEquals(rates[conversionRate.rates[0].currency], conversionRate.rates[0].rate)
    }

}
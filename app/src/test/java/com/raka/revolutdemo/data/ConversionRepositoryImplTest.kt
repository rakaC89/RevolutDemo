package com.raka.revolutdemo.data

import com.raka.revolutdemo.data.api.service.ConversionService
import com.raka.revolutdemo.data.cache.database.dao.ConversionDao
import com.raka.revolutdemo.domain.exception.DomainException
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConversionRepositoryImplTest {

    @RelaxedMockK
    private lateinit var conversionDao: ConversionDao

    private lateinit var server: MockWebServer
    private lateinit var sut: ConversionRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        server = MockWebServer()
        server.start()

        val service = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ConversionService::class.java)

        sut = ConversionRepositoryImpl(service, conversionDao)
    }

    /**
     * Integration test for currency rate api
     */
    @Test
    fun currencyRate() {
        val response = MockResponse().apply {
            //language=JSON
            setBody("""
               {
  "base": "EUR",
  "date": "2018-09-06",
  "rates": {
    "AUD": 1.6213,
    "BGN": 1.9617,
    "BRL": 4.8062,
    "CAD": 1.5384,
    "CHF": 1.1309,
    "CNY": 7.969,
    "CZK": 25.792,
    "DKK": 7.4792,
    "GBP": 0.90095,
    "HKD": 9.1599,
    "HRK": 7.4565,
    "HUF": 327.47,
    "IDR": 17376.0,
    "ILS": 4.1832,
    "INR": 83.97,
    "ISK": 128.18,
    "JPY": 129.94,
    "KRW": 1308.7,
    "MXN": 22.433,
    "MYR": 4.8265,
    "NOK": 9.8054,
    "NZD": 1.7686,
    "PHP": 62.781,
    "PLN": 4.3313,
    "RON": 4.6525,
    "RUB": 79.814,
    "SEK": 10.623,
    "SGD": 1.6048,
    "THB": 38.245,
    "TRY": 7.6512,
    "USD": 1.1669,
    "ZAR": 17.877
  }
}
            """)
        }
        server.enqueue(response)
        sut.getCurrencyRate("EUR")
    }

    @Test
    fun genericHttpException() {
        server.enqueue(MockResponse().apply {
            setResponseCode(422)
            setBody("""
               {
    "error": "Invalid base"
}
            """)
        })

        try {
            sut.getCurrencyRate("ImproperBase")
        } catch (e: DomainException) {
            assertThat(e.error).isInstanceOf(DomainException.Error.GenericError::class.java)
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}
package com.raka.revolutdemo.domain.interactor

import com.raka.revolutdemo.domain.model.ConversionRate
import com.raka.revolutdemo.domain.repository.ConversionRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test

class GetOfflineDataTest {

    @MockK
    lateinit var conversionRepository: ConversionRepository

    @InjectMockKs
    lateinit var sut: GetOfflineData

    @Before
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `emits conversion rate`() {
        val mockConversionRate = mockk<ConversionRate>()
        every { conversionRepository.getCurrencyRateFromDb() } returns Maybe.just(mockConversionRate)

        sut.execute().test().assertValue(mockConversionRate)
    }

    @Test
    fun `emits no value if source is empty`() {
        every { conversionRepository.getCurrencyRateFromDb() } returns Maybe.empty()

        sut.execute().test().assertNoValues()
    }

    @Test
    fun `emits no value if source throws error`() {
        every { conversionRepository.getCurrencyRateFromDb() } returns Maybe.error(Throwable())

        sut.execute().test().assertNoValues()
    }
}
package com.raka.revolutdemo.domain.interactor

import com.raka.revolutdemo.domain.exception.DomainException
import com.raka.revolutdemo.domain.model.ConversionRate
import com.raka.revolutdemo.domain.repository.ConversionRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class GetConversionRatesTest {

    @MockK
    lateinit var conversionRepository: ConversionRepository

    @InjectMockKs
    lateinit var sut: GetConversionRates

    private val testScheduler = TestScheduler()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        RxJavaPlugins.reset()
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
    }

    @After
    fun tearDown() = RxJavaPlugins.reset()

    @Test
    fun `emits conversion rate every second`() {
        val mockConversionRate = mockk<ConversionRate>()
        val base = "Galleon"
        every { conversionRepository.getCurrencyRate(base) } returns Observable.just(mockConversionRate)

        val testObservable = sut.execute(base).test()

        testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
        testObservable.assertEmpty()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testObservable.assertValue(mockConversionRate)
    }

    @Test
    fun `emits error and terminates`() {
        val base = "Galleon"
        every { conversionRepository.getCurrencyRate(base) }returns
                Observable.error(DomainException(DomainException.Error.GenericError))

        val testObservable = sut.execute(base).test()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testObservable.assertTerminated()
        testObservable.assertErrorMessage("Something went wrong")
    }

}
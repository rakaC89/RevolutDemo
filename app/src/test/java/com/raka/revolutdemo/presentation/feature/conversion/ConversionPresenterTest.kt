package com.raka.revolutdemo.presentation.feature.conversion

import com.raka.revolutdemo.common.rx.SchedulersProvider
import com.raka.revolutdemo.domain.exception.DomainException
import com.raka.revolutdemo.domain.interactor.GetConversionRates
import com.raka.revolutdemo.domain.interactor.GetOfflineData
import com.raka.revolutdemo.domain.model.ConversionRate
import com.raka.revolutdemo.domain.model.Rate
import com.raka.revolutdemo.testutil.TestSchedulersProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Maybe
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class ConversionPresenterTest {

    @RelaxedMockK
    lateinit var getConversionRates: GetConversionRates
    @RelaxedMockK
    lateinit var getOfflineData: GetOfflineData
    @RelaxedMockK
    lateinit var view: ConversionPresenter.View

    private val schedulers: SchedulersProvider = TestSchedulersProvider.INSTANCE
    private val sut by lazy {
        ConversionPresenter(getConversionRates, getOfflineData, schedulers) }

    @Before
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `display offline data if available`() {
        val mockConversionRate = ConversionRate("", "" , listOf(Rate("Galleon", BigDecimal.ONE)))
        every { getOfflineData.execute() } returns Maybe.just(mockConversionRate)

        sut.onViewAttached(view)

        verify(exactly = 1) { view.updateRates(listOf(Rate("Galleon", BigDecimal.ONE))) }
    }

    @Test
    fun `display api data successfully`() {
        val mockConversionRate = ConversionRate("EUR", "" , listOf(Rate("Galleon", BigDecimal(1.5))))
        every { getConversionRates.execute("EUR") } returns Observable.just(mockConversionRate)

        sut.onViewAttached(view)

        verify { view.showLoading() }
        verify { view.hideLoading() }
        verify(exactly = 1) { view.updateRates(listOf(Rate(mockConversionRate.base, BigDecimal.ONE)) + mockConversionRate.rates) }
    }

    @Test
    fun `display generic error on api failure`() {
        every { getConversionRates.execute("EUR") } returns Observable.error(DomainException(DomainException.Error.GenericError))

        sut.onViewAttached(view)

        verify { view.showLoading() }
        verify { view.hideLoading() }
        verify(exactly = 1) { view.showErrorView(DomainException.Error.GenericError.description) }
    }

    @Test
    fun `display network error on no internet`() {
        every { getConversionRates.execute("EUR") } returns Observable.error(DomainException(DomainException.Error.NoInternet))

        sut.onViewAttached(view)

        verify { view.showLoading() }
        verify { view.hideLoading() }
        verify(exactly = 1) { view.showErrorView(DomainException.Error.NoInternet.description) }
    }
}
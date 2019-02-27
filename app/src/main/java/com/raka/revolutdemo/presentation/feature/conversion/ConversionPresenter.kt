package com.raka.revolutdemo.presentation.feature.conversion

import com.raka.revolutdemo.common.rx.SchedulersProvider
import com.raka.revolutdemo.domain.interactor.GetConversionRates
import com.raka.revolutdemo.domain.interactor.GetOfflineData
import com.raka.revolutdemo.domain.model.Rate
import com.raka.revolutdemo.presentation.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import java.math.BigDecimal
import javax.inject.Inject

const val DEFAULT_BASE = "EUR"

class ConversionPresenter @Inject constructor(
    private val getConversionRates: GetConversionRates,
    private val getOfflineData: GetOfflineData,
    private val schedulers: SchedulersProvider
) : BasePresenter<ConversionPresenter.View>() {

    private val compositeDisposable = CompositeDisposable()
    private var lastBase: String = DEFAULT_BASE

    override fun onViewAttached(view: View) {
        super.onViewAttached(view)
        displayOfflineData()
        getConversionRate(DEFAULT_BASE)
    }

    fun retry() {
        getConversionRate(DEFAULT_BASE)
    }

    fun getConversionRate(base: String) {
        view?.showLoading()
        if (base != lastBase) {
            compositeDisposable.clear()
        }
        compositeDisposable.add(
            getConversionRates.execute(base)
                .doOnSubscribe { lastBase = base }
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe(
                    {
                        view?.hideLoading()
                        view?.updateRates(listOf(Rate(it.base, BigDecimal.ONE)) + it.rates)
                    },
                    {
                        view?.hideLoading()
                        view?.showErrorView(it.message ?: "Oops!")
                    }
                )
        )
    }

    private fun displayOfflineData() {
        compositeDisposable.add(
            getOfflineData.execute()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .onErrorComplete()
                .subscribe{ view?.updateRates(it.rates) }
        )
    }

    override fun onViewDetached() {
        super.onViewDetached()
        compositeDisposable.clear()
    }

    interface View : BasePresenter.View {
        fun showLoading()
        fun hideLoading()
        fun showErrorView(message: String)
        fun updateRates(rates: List<Rate>)
    }
}
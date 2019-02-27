package com.raka.revolutdemo.domain.interactor

import com.raka.revolutdemo.domain.model.ConversionRate
import com.raka.revolutdemo.domain.repository.ConversionRepository
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class GetConversionRates @Inject constructor(private val repository: ConversionRepository) {

    fun execute(base: String): Observable<ConversionRate> {
        return Observable
            .interval(1, TimeUnit.SECONDS)
            .flatMap { repository.getCurrencyRate(base) }
    }
}
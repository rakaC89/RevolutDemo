package com.raka.revolutdemo.domain.repository

import com.raka.revolutdemo.domain.model.ConversionRate
import io.reactivex.Maybe
import io.reactivex.Observable

interface ConversionRepository {

    fun getCurrencyRate(base: String): Observable<ConversionRate>

    fun getCurrencyRateFromDb(): Maybe<ConversionRate>
}
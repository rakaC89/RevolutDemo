package com.raka.revolutdemo.domain.interactor

import com.raka.revolutdemo.domain.model.ConversionRate
import com.raka.revolutdemo.domain.repository.ConversionRepository
import io.reactivex.Maybe
import javax.inject.Inject

class GetOfflineData @Inject constructor(private val repository: ConversionRepository) {

    fun execute(): Maybe<ConversionRate> {
        return repository.getCurrencyRateFromDb()
    }
}
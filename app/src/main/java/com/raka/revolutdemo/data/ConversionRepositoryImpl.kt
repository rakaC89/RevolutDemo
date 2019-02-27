package com.raka.revolutdemo.data

import com.raka.revolutdemo.data.api.mapper.ApiConversionMapper
import com.raka.revolutdemo.data.api.service.ConversionService
import com.raka.revolutdemo.data.cache.database.dao.ConversionDao
import com.raka.revolutdemo.data.cache.mapper.CacheConversionMapper
import com.raka.revolutdemo.domain.exception.DomainException
import com.raka.revolutdemo.domain.model.ConversionRate
import com.raka.revolutdemo.domain.repository.ConversionRepository
import io.reactivex.Maybe
import io.reactivex.Observable
import okhttp3.Request
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class ConversionRepositoryImpl(
    private val conversionService: ConversionService,
    private val conversionDao: ConversionDao
) : ConversionRepository {

    override fun getCurrencyRate(base: String): Observable<ConversionRate> {
        return Observable
            .fromCallable {
                val conversionRate = getCurrencyRateFromApi(base)
                updateCurrencyRateInDb(conversionRate)
                conversionRate}
    }

    override fun getCurrencyRateFromDb(): Maybe<ConversionRate> {
        return Maybe.fromCallable {
            conversionDao.queryCurrencyRate()
                .let { CacheConversionMapper.toDomain(it) }
        }
    }

    private fun updateCurrencyRateInDb(conversionRate: ConversionRate) {
        conversionDao.insertOrUpdateCurrencyRate(
            CacheConversionMapper.fromDomain(conversionRate)
        )
    }

    private fun getCurrencyRateFromApi(base: String): ConversionRate {
        return conversionService.currencyRates(base).executeCall()
            .let { ApiConversionMapper.toDomain(it) }
    }

    private fun <T> Call<T>.executeCall(): T {
        val response = execute()
        if (response.isSuccessful) {
            return response.body() ?: handleError(request(), response)
        } else {
            handleError(request(), response)
        }
    }

    private fun <T> handleError(request: Request, response: Response<T>): Nothing {
        val body = response.errorBody()?.string()
        Timber.i("HTTP Error (request path=${request.url().encodedPath()})")
        if (body != null) {
            Timber.w(
                """
                HTTP Error (code=${response.code()}
                message=${response.message()}
                body=$body))
            """
            )
        } else {
            Timber.w("HTTP Error (code=${response.code()}, message=${response.message()})")
        }
        throw DomainException(DomainException.Error.GenericError)
    }
}
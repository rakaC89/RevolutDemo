package com.raka.revolutdemo.data.api.service

import com.raka.revolutdemo.data.api.model.ConversionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ConversionService {

    @GET("latest")
    fun currencyRates(@Query("base") base: String): Call<ConversionResponse>
}
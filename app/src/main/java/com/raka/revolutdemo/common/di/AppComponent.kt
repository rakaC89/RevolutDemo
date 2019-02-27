package com.raka.revolutdemo.common.di

import android.app.Application
import com.google.gson.Gson
import com.raka.revolutdemo.common.rx.SchedulersProvider
import com.raka.revolutdemo.data.utils.ConnectivityInterceptor
import com.raka.revolutdemo.domain.repository.ConversionRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(application: Application)

    fun gson(): Gson

    fun connectivityInterceptor(): ConnectivityInterceptor

    fun conversionRepository(): ConversionRepository

    fun schedulersProvider(): SchedulersProvider
}
package com.raka.revolutdemo.common.di

import android.app.Application
import com.google.gson.Gson
import com.raka.revolutdemo.common.rx.SchedulersProvider
import com.raka.revolutdemo.data.utils.ConnectivityInterceptor
import com.raka.revolutdemo.domain.repository.ConversionRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestAppModule::class])
interface TestAppComponent: AppComponent {

    override fun inject(application: Application)

    override fun gson(): Gson

    override fun connectivityInterceptor(): ConnectivityInterceptor

    override fun conversionRepository(): ConversionRepository

    override fun schedulersProvider(): SchedulersProvider

}
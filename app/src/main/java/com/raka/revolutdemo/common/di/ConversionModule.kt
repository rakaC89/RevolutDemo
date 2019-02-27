package com.raka.revolutdemo.common.di

import com.raka.revolutdemo.common.rx.SchedulersProvider
import com.raka.revolutdemo.domain.interactor.GetConversionRates
import com.raka.revolutdemo.domain.interactor.GetOfflineData
import com.raka.revolutdemo.presentation.feature.conversion.ConversionPresenter
import dagger.Module
import dagger.Provides

@Module
class ConversionModule(val view: ConversionPresenter.View) {

    @Provides
    fun provideView(): ConversionPresenter.View = view

    @Provides
    fun providePresenter(
        getConversionRates: GetConversionRates,
        getOfflineData: GetOfflineData,
        schedulersProvider: SchedulersProvider
    ): ConversionPresenter {
        return ConversionPresenter(getConversionRates, getOfflineData, schedulersProvider)
    }

}
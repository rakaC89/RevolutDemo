package com.raka.revolutdemo.common.di

import com.raka.revolutdemo.presentation.feature.conversion.ConversionActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ConversionModule::class])
interface ConversionComponent {
    fun inject(target: ConversionActivity)
}
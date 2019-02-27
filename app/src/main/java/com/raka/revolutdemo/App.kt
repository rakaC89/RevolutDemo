package com.raka.revolutdemo

import android.app.Application
import com.raka.revolutdemo.common.di.AppComponent
import com.raka.revolutdemo.common.di.AppModule
import com.raka.revolutdemo.common.di.DaggerAppComponent
import timber.log.Timber

open class App : Application() {

    lateinit var component:AppComponent

    override fun onCreate() {
        super.onCreate()
        initTimber()

        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        component.inject(this)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun getAppComponent(): AppComponent? = component
}
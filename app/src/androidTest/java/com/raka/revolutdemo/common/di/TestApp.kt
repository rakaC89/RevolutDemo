package com.raka.revolutdemo.common.di

import com.raka.revolutdemo.App


class TestApp: App() {

    override fun onCreate() {
        super.onCreate()

        component = DaggerTestAppComponent.builder()
            .testAppModule(TestAppModule(this))
            .build()
        component.inject(this)
    }

}
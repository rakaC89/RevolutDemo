package com.raka.revolutdemo.utils

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.raka.revolutdemo.common.di.TestApp

class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}
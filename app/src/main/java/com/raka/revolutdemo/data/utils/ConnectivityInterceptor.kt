package com.raka.revolutdemo.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.raka.revolutdemo.domain.exception.DomainException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            throw DomainException(DomainException.Error.NoInternet)
        }
        return chain.proceed(chain.request())
    }

    /**
     * Test if the user has connection data
     * @return True if connected
     *
     * @see [Android documentation] (https://developer.android.com/training/monitoring-device-state/connectivity-monitoring#kotlin)
     */
    private fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

        return activeNetwork?.isConnected == true
    }
}
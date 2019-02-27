package com.raka.revolutdemo.common.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.raka.revolutdemo.App
import com.raka.revolutdemo.BuildConfig
import com.raka.revolutdemo.common.rx.SchedulersProvider
import com.raka.revolutdemo.common.rx.SchedulersProviderImpl
import com.raka.revolutdemo.data.ConversionRepositoryImpl
import com.raka.revolutdemo.data.api.service.ConversionService
import com.raka.revolutdemo.data.cache.database.ConversionDatabase
import com.raka.revolutdemo.data.utils.ConnectivityInterceptor
import com.raka.revolutdemo.data.utils.NETWORK_TIMEOUT_SECONDS
import com.raka.revolutdemo.domain.repository.ConversionRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return app
    }

    @Provides
    @Singleton
    fun gson(): Gson = Gson()

    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun connectivityInterceptor(context: Context)
            : ConnectivityInterceptor = ConnectivityInterceptor(context)

    @Provides
    fun okClient(connectivityInterceptor: ConnectivityInterceptor,
                 loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(connectivityInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun conversionService(
        okClient: OkHttpClient,
        gson: Gson
    ): ConversionService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.CONVERSION_ENDPOINT)
            .client(okClient)
            .build()
            .create(ConversionService::class.java)
    }

    @Provides
    fun conversionDatabase(context: Context): ConversionDatabase {
        return Room.databaseBuilder(
            context,
            ConversionDatabase::class.java,
            ConversionDatabase::class.java.simpleName)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun conversionRepository(conversionService: ConversionService, conversionDatabase: ConversionDatabase): ConversionRepository {
        return ConversionRepositoryImpl(conversionService, conversionDatabase.conversionDao())
    }

    @Provides
    @Reusable
    fun schedulersProvider(): SchedulersProvider = SchedulersProviderImpl()
}
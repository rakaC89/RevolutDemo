package com.raka.revolutdemo.common.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.raka.revolutdemo.common.rx.SchedulersProvider
import com.raka.revolutdemo.common.rx.SchedulersProviderImpl
import com.raka.revolutdemo.data.ConversionRepositoryImpl
import com.raka.revolutdemo.data.api.service.ConversionService
import com.raka.revolutdemo.data.cache.database.ConversionDatabase
import com.raka.revolutdemo.data.utils.ConnectivityInterceptor
import com.raka.revolutdemo.domain.repository.ConversionRepository
import com.raka.revolutdemo.utils.MockedInterceptor
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class TestAppModule(private val app: TestApp) {

    private val BASE_URL = "https://localhost:8080/"

    @Provides
    @Singleton
    fun provideContext(): Context {
        return app
    }

    @Provides
    @Singleton
    fun gson(): Gson = Gson()

    @Provides
    @Singleton
    fun connectivityInterceptor(context: Context)
            : ConnectivityInterceptor = ConnectivityInterceptor(context)


    @Provides
    fun okClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor( MockedInterceptor())
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
            .baseUrl(BASE_URL)
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
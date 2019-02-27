package com.raka.revolutdemo.data.cache.database.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.raka.revolutdemo.data.cache.database.ConversionDatabase
import com.raka.revolutdemo.data.cache.model.ConversionCache
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal

@RunWith(AndroidJUnit4::class)
class ConversionDaoTest {
    private lateinit var database: ConversionDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            ConversionDatabase::class.java).build()
    }

    @After
    fun tearDown() = database.close()

    @Test
    fun insertOrUpdateCurrencyRate() {
        val conversionCacheList = listOf(ConversionCache(
            currency = "Galleon",
            rate = BigDecimal(1.5)))

        database.conversionDao().insertOrUpdateCurrencyRate(conversionCacheList)
    }

    @Test
    fun queryCurrencyRate() {
        val conversionCacheList = listOf(ConversionCache(
            currency = "Galleon",
            rate = BigDecimal(1.5)))

        database.conversionDao().insertOrUpdateCurrencyRate(conversionCacheList)

        val expectedCacheList = database.conversionDao().queryCurrencyRate()
        Assert.assertEquals(conversionCacheList, expectedCacheList)
    }
}
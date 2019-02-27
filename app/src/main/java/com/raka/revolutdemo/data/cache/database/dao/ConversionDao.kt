package com.raka.revolutdemo.data.cache.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.raka.revolutdemo.data.cache.model.ConversionCache

@Dao
interface ConversionDao {

    @Insert(onConflict = REPLACE)
    fun insertOrUpdateCurrencyRate(conversionCache: List<ConversionCache>)

    @Query("SELECT * FROM ConversionCache")
    fun queryCurrencyRate(): List<ConversionCache>
}
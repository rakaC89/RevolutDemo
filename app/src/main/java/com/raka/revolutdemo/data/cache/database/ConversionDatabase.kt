package com.raka.revolutdemo.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raka.revolutdemo.data.cache.database.dao.ConversionDao
import com.raka.revolutdemo.data.cache.model.ConversionCache

@Database(entities = [ConversionCache::class], version = 1, exportSchema = false)
abstract class ConversionDatabase : RoomDatabase() {

    abstract fun conversionDao(): ConversionDao
}
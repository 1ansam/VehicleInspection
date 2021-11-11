package com.yxf.vehicleinspection.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yxf.vehicleinspection.bean.response.DataDictionaryR003Response
import com.yxf.vehicleinspection.bean.response.SystemParamsR015Response

/**
 *   author:yxf
 *   time:2021/11/9
 */
@Database(entities = [DataDictionaryR003Response::class,SystemParamsR015Response::class],version = 2,exportSchema = false)
abstract class DataDictionaryDatabase : RoomDatabase() {
    abstract fun dataDictionaryDao(): DataDictionaryDao
    abstract fun systemParamsDao() : SystemParamsDao
    companion object {
        @Volatile
        private var INSTANCE: DataDictionaryDatabase? = null

        fun getDatabase(
            context: Context,
        ): DataDictionaryDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataDictionaryDatabase::class.java,
                    "Database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
package com.yxf.vehicleinspection.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yxf.vehicleinspection.bean.JsCsCode

/**
 *   author:yxf
 *   time:2021/10/8
 */
@Database(entities = [JsCsCode::class],version = 3,exportSchema = false)
abstract class JsCsCodeDatabase : RoomDatabase() {
    abstract fun jsCsCodeDao() : JsCsCodeDao
    companion object {
        @Volatile
        private var INSTANCE: JsCsCodeDatabase? = null

        fun getDatabase(
            context: Context,
        ): JsCsCodeDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JsCsCodeDatabase::class.java,
                    "MyDatabase"
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
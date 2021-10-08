package com.yxf.vehicleinspection

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.yxf.vehicleinspection.repository.JsCsCodeRepository
import com.yxf.vehicleinspection.room.JsCsCodeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApp : Application(){

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        val database by lazy { JsCsCodeDatabase.getDatabase(context) }
        val repository by lazy { JsCsCodeRepository(database.jsCsCodeDao()) }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
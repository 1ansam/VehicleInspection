package com.yxf.vehicleinspection

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.yxf.vehicleinspection.repository.*
import com.yxf.vehicleinspection.room.DataDictionaryDatabase

class MyApp : Application(){
    val database by lazy { DataDictionaryDatabase.getDatabase(context) }
    val dataDictionaryRepository by lazy { DataDictionaryRepository(database.dataDictionaryDao()) }
    val exteriorRepository by lazy{ ExteriorRepository()}
    val userInfoRepository by lazy{ UserInfoRepository()}
    val vehicleAllInfoRepository by lazy{ VehicleAllInfoRepository()}
    val vehicleImageRepository by lazy{ VehicleImageRepository()}
    val vehicleInspectionItemRepository by lazy{ VehicleInspectionItemRepository()}
    val vehicleQueueRepository by lazy{ VehicleQueueRepository()}
    val vehicleVideoRepository by lazy{ VehicleVideoRepository()}
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
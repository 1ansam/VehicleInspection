package com.yxf.vehicleinspection

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.yxf.vehicleinspection.repository.*
import com.yxf.vehicleinspection.room.DataDictionaryDatabase

class MyApp : Application(){
    val database by lazy { DataDictionaryDatabase.getDatabase(context) }
    val dataDictionaryRepository by lazy { DataDictionaryRepository(database.dataDictionaryDao()) }
    val systemParamsRepository by lazy { SystemParamsRepository(database.systemParamsDao()) }
    val inspectionItemRepository by lazy{ InspectionItemRepository()}
    val userInfoRepository by lazy{ UserInfoRepository()}
    val vehicleAllInfoRepository by lazy{ VehicleAllInfoRepository()}
    val vehicleImageRepository by lazy{ VehicleImageRepository()}
    val vehicleInspectionItemRepository by lazy{ VehicleInspectionItemRepository()}
    val vehicleQueueRepository by lazy{ VehicleQueueRepository()}
    val vehicleVideoRepository by lazy{ VehicleVideoRepository()}
    val signatureRepository by lazy { SignatureRepository() }
    val serverTimeRepository by lazy { ServerTimeRepository() }
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
package com.yxf.vehicleinspection

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.yxf.vehicleinspection.repository.*
import com.yxf.vehicleinspection.room.DataDictionaryDatabase

class MyApp : Application(),Application.ActivityLifecycleCallbacks{
    private val database by lazy { DataDictionaryDatabase.getDatabase(context) }
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
    val registerRepository by lazy { RegisterRepository() }
    val administrativeRepository by lazy { AdministrativeRepository(database.administrativeDao()) }
    var activityCount = 0
    var isBackground = false
    var isScreenOf = false
    var backgroundStamp = 0L
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var manager: ActivityManager
    }

    override fun onCreate() {
        super.onCreate()
        this.registerActivityLifecycleCallbacks(this)
        context = applicationContext
        manager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {


    }

    override fun onActivityStopped(activity: Activity) {
        if (manager.getRunningTasks(1)[0].topActivity?.packageName != context.packageName){
            Toast.makeText(context, "车辆检验正在后台运行", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (manager.getRunningTasks(1)[0].topActivity?.packageName != context.packageName){
            Toast.makeText(context, "已结束车辆检验所有进程", Toast.LENGTH_SHORT).show()
        }
    }
}
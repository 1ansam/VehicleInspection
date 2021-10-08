package com.yxf.vehicleinspection.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 *   author:yxf
 *   time:2021/10/8
 */
class JsCsCodeDbHelper(val context : Context) : SQLiteOpenHelper(context,"MyDatabase",null,3) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val sql ="create table if not exists MyDatabase (Fl text, FlMc text, Dm text, Mc text, Bz1 text, Bz2 text)"
        p0?.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val sql = "DROP TABLE IF EXISTS MyDatabase"
        p0?.execSQL(sql)
        onCreate(p0)
    }
}
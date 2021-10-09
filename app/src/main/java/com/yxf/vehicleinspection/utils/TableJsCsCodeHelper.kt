package com.yxf.vehicleinspection.utils

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

/**
 *   author:yxf
 *   time:2021/10/8
 */
class TableJsCsCodeHelper(val context : Context) {
    private val JS_CS_CODE_COLUMNS = arrayOf("Fl","FlMc","Dm","Mc","Bz1","Bz2")
    private val jsCsCodeDbHelper = JsCsCodeDbHelper(context)

    fun isDataExist() : Boolean{
        var count = 0
        var db : SQLiteDatabase? = null
        var cursor : Cursor? = null
        try {
            db = jsCsCodeDbHelper.readableDatabase
            cursor = db.query(JsCsCodeDbHelper.TABLE_NAME, arrayOf("COUNT(*)"),null,null,null,null,null)
            if (cursor.moveToFirst()){
                count = cursor.getInt(0)
            }
            if (count > 0) return true
        }catch (e : Exception){
            e.message
        }
        finally {
            cursor?.close()
            db?.close()
        }
        return false
    }

    fun deleteJsCsCodeTable() {
        var db : SQLiteDatabase? = null
        try {
            db = jsCsCodeDbHelper.writableDatabase
            db.beginTransaction()
            db.execSQL("DELETE FROM" + JsCsCodeDbHelper.TABLE_NAME)
            db.setTransactionSuccessful()
        }catch (e : Exception){
            e.message
        }
        finally {
            db?.endTransaction()
            db?.close()
        }
    }

    @SuppressLint("Range")
    fun getDm(Fl : String, Mc : String) : String{
        var db : SQLiteDatabase? = null
        var cursor : Cursor? = null
        try {
            db = jsCsCodeDbHelper.readableDatabase
            cursor = db.query(JsCsCodeDbHelper.TABLE_NAME,JS_CS_CODE_COLUMNS,"Fl=? and Mc=?",
                arrayOf(Fl,Mc),null,null,null)
            if (cursor.count > 0){
                var strings = ""
                var i = 0
                while (cursor.moveToNext()){
                    strings = cursor.getString(cursor.getColumnIndex("Dm"))
                    i++
                }
                return strings
            }
        }catch (e : Exception){
            e.message
        }
        finally {
            cursor?.close()
            db?.close()
        }
        return ""
    }

    @SuppressLint("Range")
    fun getMc(Fl : String, Dm : String) : String{
        var db : SQLiteDatabase? = null
        var cursor : Cursor? = null
        try {
            db = jsCsCodeDbHelper.readableDatabase
            cursor = db.query(JsCsCodeDbHelper.TABLE_NAME,JS_CS_CODE_COLUMNS,"Fl=? and Dm=?",
                arrayOf(Fl,Dm),null,null,null)
            if (cursor.count > 0){
                var strings = ""
                var i = 0
                while (cursor.moveToNext()){
                    strings = cursor.getString(cursor.getColumnIndex("Mc"))
                    i++
                }
                return strings
            }
        }catch (e : Exception){
            e.message
        }
        finally {
            cursor?.close()
            db?.close()
        }
        return ""
    }
}
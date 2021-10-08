package com.yxf.vehicleinspection.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *   author:yxf
 *   time:2021/9/27
 *   数据字典(JsCsCode)
 *   @param Fl	性质分类	varchar(2)	NOT NULL
 *   @param FlMc	分类名称	varchar(16)	NOT NULL
 *   @param Dm	性质代码	varchar(4)	NOT NULL
 *   @param Mc	性质名称	varchar(64)
 *   @param Bz1	性质备注1	varchar(128)
 *   @param Bz2	性质备注2	varchar(128)

 */
@Entity(tableName = "JsCsCode")
data class JsCsCode(val Fl : String,
                    val FlMc : String,
                    val Dm : String?,
                    val Mc : String?,
                    val Bz1 : String?,
                    val Bz2 : String?,
                    @PrimaryKey val InfoID : String
)
{
    constructor(
        Fl: String,
        FlMc: String,
        InfoID: String
    ) : this(
        Fl,
        FlMc,
        null,
        null,
        null,
        null,
        InfoID
    )
}
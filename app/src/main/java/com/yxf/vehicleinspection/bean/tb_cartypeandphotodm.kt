package com.yxf.vehicleinspection.bean

import java.util.*

/**
 *   author:yxf
 *   time:2021/9/27
 *   车辆类型对应外观照片表(tb_cartypeandphotodm)
 *   @param id	自增编号	int	NOT NULL
 *   @param pkey	唯一标识	varchar(64)	NOT NULL
 *   @param cartype	-车辆类型	varchar(8)
 *   @param photoDm	照片代码	varchar(8)
 *   @param ajps	安检拍摄	varchar(2)
 *   @param hjps	环检拍摄	varchar(2)
 *   @param zjps	综检拍摄	varchar(2)
 *   @param gpysc	高拍仪拍摄	varchar(2)
 *   @param iswork	是否有效	varchar(2)
 *   @param changeTime	修改时间	smalldatetime

 */
data class tb_cartypeandphotodm(val id : Int,
                           val pkey : String,
                           val cartype : String?,
                           val photoDm : String?,
                           val ajps : String?,
                           val hjps : String?,
                           val zjps : String?,
                           val gpysc : String?,
                           val iswork : String?,
                           val changeTime : Date?
){
    constructor(
        id: Int,
        pkey: String,
    ) : this(
        id,
        pkey,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}
package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   车辆类型信息表(T_CarType)
 *   @param id	自增编号	int	NOT NULL
 *   @param lxbh	类型编号	varchar(8)	NOT NULL
 *   @param lxms	类型描述	varchar(200)
 *   @param Bz01	备注	varchar(200)

 */
data class T_CarType(val id : Int,
                val lxbh : String,
                val lxms : String?,
                val Bz01 : String?
)
{
    constructor(
        id: Int,
        lxbh: String,
    ) : this(
        id,
        lxbh,
        null,
        null
    )
}
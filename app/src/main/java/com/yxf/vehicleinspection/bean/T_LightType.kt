package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   机动车前照灯制信息表(T_LightType)
 *   @param id	自增编号	int	NOT NULL
 *   @param dzbh	灯制编号	varchar(4)
 *   @param dzms	灯制描述	varchar(200)
 *   @param Bz01	备注	varchar(200)

 */
data class T_LightType(val id : String,
                       val dzbh : String?,
                       val dzms : String?,
                       val Bz01 : String?
){
    constructor(
        id: String,
    ) : this(
        id,
        null,
        null,
        null
    )
}
package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   前照灯发光强度国标信息表(T_Criterion_Light_Lux)
 *   @param id	自增序列号	int	NOT NULL
 *   @param zyc	在用车	nvarchar(50)
 *   @param jdclx	机动车类型	nvarchar(50)
 *   @param qzdz	前照灯制	nvarchar(50)
 *   @param fgqd	发光强度	nvarchar(50)

 */
data class T_Criterion_Light_Lux(val id : Int,
                                 val zyc : String?,
                                 val jdclx : String?,
                                 val qzdz : String?,
                                 val fgqd : String?
){
    constructor(
        id: Int,
        zyc: String,
    ) : this(
        id,
        zyc,
        null,
        null,
        null
    )
}
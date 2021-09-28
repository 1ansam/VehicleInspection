package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   驻车制动率标准信息表(T_Criterion_Hand)
 *   @param id	自增编号	int	NOT NULL
 *   @param bzbh	标准编号	nvarchar(50)	NOT NULL
 *   @param zzl	总质量	char(1)
 *   @param zdl	制动率	nvarchar(50)
 *   @param Bz01	备注	nvarchar(200)

 */
data class T_Criterion_Hand(val id : String,
                            val bzbh : String,
                            val zzl : String?,
                            val zdl : String?,
                            val Bz01 : String?,
){
    constructor(
        id: String,
        bzbh: String,
    ) : this(
        id,
        bzbh,
        null,
        null,
        null
    )
}
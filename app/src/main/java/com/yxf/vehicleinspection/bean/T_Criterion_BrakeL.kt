package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   制动率标准信息表(T_Criterion_BrakeL)
 *   @param id	自增编号	int	NOT NULL
 *   @param bzbh	标准编号	nvarchar(10)	NOT NULL
 *   @param jdclx	机动车类型	nvarchar(10)
 *   @param kz	空载	char(1)
 *   @param jysb	检验设备	nvarchar(8)
 *   @param zczdl	整车制动率	nvarchar(8)
 *   @param qzzdl	前轴制动率	nvarchar(50)
 *   @param hzzdl	后轴制动率	nvarchar(50)
 *   @param Bz01	备注	nvarchar(200)

 */
data class T_Criterion_BrakeL(val id : Int,
                              val bzbh : String,
                              val jdclx : String?,
                              val kz : String?,
                              val jysb : String?,
                              val zczdl : String?,
                              val qzzdl : String?,
                              val hzzdl : String?,
                              val Bz01 : String?
){
    constructor(
        id: Int,
        bzbh: String,
    ) : this(
        id,
        bzbh,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}
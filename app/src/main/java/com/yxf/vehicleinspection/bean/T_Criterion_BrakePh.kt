package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   制动不平衡率标准信息表(T_Criterion_BrakePh)
 *   @param id	自增编号	int	NOT NULL
 *   @param bzbh	标准编号	nvarchar(50)	NOT NULL
 *   @param zyc	在用车	char(1)
 *   @param qzzdlph	前轴制动率平衡	nvarchar(50)
 *   @param hzzdlph	后轴制动力平衡	nvarchar(50)
 *   @param hzzdlphUnder60	后轴制动率小于60时的制动力平衡	nvarchar(50)
 *   @param Bz01	备注	nvarchar(200)

 */
data class T_Criterion_BrakePh(val id : String,
                               val bzbh : String,
                               val zyc : String?,
                               val qzzdlph : String?,
                               val hzzdlph : String?,
                               val hzzdlphUnder60 : String?,
                               val Bz01 : String?
){
    constructor(
        id: String,
        bzbh: String,
    ) : this(
        id,
        bzbh,
        null,
        null,
        null,
        null,
        null
    )
}
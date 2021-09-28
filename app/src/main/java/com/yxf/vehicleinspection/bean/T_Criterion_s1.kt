package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   车速表国标信息表(T_Criterion_s1)
 *   @param id	自增编号	Int	NOT NULL
 *   @param datamax	最大值	nvarchar(50)
 *   @param datamin	最小值	nvarchar(50)

 */
class T_Criterion_s1(val id : String,
                     val datamax : String?,
                     val datamin : String?
){
    constructor(
        id: String,
    ) : this(
        id,
        null,
        null
    )
}
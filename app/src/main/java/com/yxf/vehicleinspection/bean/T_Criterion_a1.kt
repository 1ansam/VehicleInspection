package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   侧滑标准信息表(T_Criterion_a1)
 *   @param id	自增编号	int	NOT NULL
 *   @param datamax	最大值	nvarchar(50)
 *   @param datamin	最小值	nvarchar(50)

 */
data class T_Criterion_a1(val id : Int,
                     val datamax : String?,
                     val datamin : String?
){
    constructor(
        id: Int,
    ) : this(
        id,
        null,
        null
    )
}
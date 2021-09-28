package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   声级标准信息表(T_Criterion_Horn)
 *   @param id	自增编号	int	NOT NULL
 *   @param bzbh	标准编号	nvarchar(50)	NOT NULL
 *   @param zdjgl	未使用	char(1)
 *   @param sjmax	声级最大值	nvarchar(50)
 *   @param sjmin	声级最小值	nvarchar(50)
 *   @param Bz01	备注	nvarchar(200)

 */
data class T_Criterion_Horn(val id : Int,
                            val bzbh : String,
                            val zdjgl : String?,
                            val sjmax : String?,
                            val sjmin : String?,
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
        null
    )
}
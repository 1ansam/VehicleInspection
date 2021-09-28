package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   自动打印队列信息表(Tb_PrintInfo)
 *   @param id	自增编号	int	NOT NULL
 *   @param lsh	流水号	varchar(24)	NOT NULL
 *   @param printAj	打印安检	varchar(2)
 *   @param printZj	打印综检	varchar(2)
 *   @param printHj	打印环检	varchar(2)

 */
data class Tb_PrintInfo(val id : Int,
                        val lsh : String,
                        val printAj : String?,
                        val printZj : String?,
                        val printHj : String?
){
    constructor(
        id: Int,
        lsh: String,
    ) : this(
        id,
        lsh,
        null,
        null,
        null
    )
}
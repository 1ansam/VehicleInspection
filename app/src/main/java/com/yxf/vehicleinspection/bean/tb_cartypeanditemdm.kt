package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   车辆类型对应检验外观项目表(tb_cartypeanditemdm)
 *   @param id	自增编号	int	NOT NULL
 *   @param pkey	唯一标识	varchar(64)	NOT NULL
 *   @param cartype	车辆类型	varchar(8)
 *   @param fldm	分类代码	varchar(8)
 *   @param itemdm	条目代码	varchar(8)
 *   @param iswork	是否有效	varchar(2)
 *   @param changetime	修改时间	smalldatetime

 */
data class tb_cartypeanditemdm(val id : Int,
                          val pkey : String,
                          val cartype : String?,
                          val fldm : String?,
                          val itemdm : String?,
                          val iswork : String?,
                          val changetime : String?
){
    constructor(
        id: Int,
        pkey: String,
    ) : this(
        id,
        pkey,
        null,
        null,
        null,
        null,
        null,
    )
}
package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   权限身份信息表(Tb_Roles)
 *   @param id	自增编号	int	NOT NULL
 *   @param dm	代码	varchar(8)	NOT NULL
 *   @param mc	名称	varchar(24)
 *   @param isWork	是否有效	varchar(1)
 *   @param bz	备注	varchar(200)

 */
data class Tb_Roles (val id : Int,
                     val dm : String,
                     val mc : String?,
                     val isWork : String?,
                     val bz : String?,
){
    constructor(
        id: Int,
        dm: String,
    ) : this(
        id,
        dm,
        null,
        null,
        null
    )
}
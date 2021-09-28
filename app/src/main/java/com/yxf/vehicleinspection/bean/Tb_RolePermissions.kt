package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   身份权限对应权限明细表(Tb_RolePermissions)
 *   @param id	自增编号	int	NOT NULL
 *   @param roleDm	身份代码	varchar(8)	NOT NULL
 *   @param authorityDm	权限代码	varchar(128)	NOT NULL
 *   @param bz	备注	varchar(50)

 */
data class Tb_RolePermissions(val id : Int,
                              val roleDm : String,
                              val authorityDm : String,
                              val bz : String?,
){
    constructor(
        id: Int,
        roleDm: String,
        authorityDm : String
    ) : this(
        id,
        roleDm,
        authorityDm,
        null
    )
}
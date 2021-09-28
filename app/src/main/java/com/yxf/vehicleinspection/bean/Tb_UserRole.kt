package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   工作人员对应身份权限表(Tb_UserRole)
 *   @param id	自增编号	int	NOT NULL
 *   @param username	用户名	varchar(64)
 *   @param roleDm	身份代码	varchar(8)
 *   @param isWork	是否有效	varchar(1)

 */
class Tb_UserRole(val id : Int,
                  val username : String?,
                  val roleDm : String?,
                  val isWork : String?
){
    constructor(
        id: Int,
    ) : this(
        id,
        null,
        null,
        null
    )
}
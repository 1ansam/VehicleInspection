package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/10/11
 */
data class UserInfo(
    val ID : String?,
    val GongHao : String?,
    val UserName : String?,
    val PassWord : String?,
    val TrueName : String?,
    val AddDate : String?,
    val RoleDm : String?
)
{
    constructor() : this(null,null,null,null,null,null,null)
}
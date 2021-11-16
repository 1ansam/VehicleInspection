package com.yxf.vehicleinspection.bean.response

import java.io.Serializable

/**
 *   author:yxf
 *   time:2021/10/11
 *   @param ID 身份证号
 *   @param GongHao 工号
 *   @param UserName 用户名
 *   @param PassWord 密码
 *   @param TrueName 真实姓名
 *   @param AddDate 创建日期
 *   @param RoleDm 权限代码

 */
data class UserInfoR001Response(
    val ID : String,
    val GongHao : String,
    val UserName : String,
    val PassWord : String,
    val TrueName : String,
    val AddDate : String,
    val RoleDm : String
) : Serializable

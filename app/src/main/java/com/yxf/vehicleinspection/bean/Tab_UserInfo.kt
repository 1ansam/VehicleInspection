package com.yxf.vehicleinspection.bean

import java.util.*

/**
 *   author:yxf
 *   time:2021/9/27
 *   用户信息表(Tab_UserInfo)
 *   @param InfoID	自增编号	int	NOT NULL
 *   @param SfzInfoID	身份证号	varchar(18)	NOT NULL
 *   @param GongHao	工号	varchar(32)	NOT NULL
 *   @param UserName	用户名	varchar(64)	NOT NULL
 *   @param PassWord	密码	varchar(64)	NOT NULL
 *   @param turename	真实姓名	varchar(128)	NOT NULL
 *   @param State	状态	varchar(4)	NOT NULL
 *   @param AddDate	添加日期	datetime	NOT NULL
 *   @param UserEndDate	用户有效期止	date
 *   @param PassEndDate	密码有效期止	date
 *   @param UseTime1	使用时间段开始	sql_variant
 *   @param UseTime2	使用时间段结束	sql_variant
 *   @param RySSBM	人员所属部门	varchar(32)
 *   @param ErrNum	密码错误次数	int
 *   @param isWork	是否有效	varchar(1)
 *   @param zjcx	准驾车型	varchar(8)
 *   @param Bz01	备注	varchar(MAX)
 *   @param Bz02	备注	varchar(32)
 *   @param Bz03	备注	varchar(32)
 *   @param qianming	签名（base64）

 */
data class Tab_UserInfo(val InfoID : String,
                        val SfzInfoID : String,
                        val GongHao : String,
                        val UserName : String,
                        val PassWord : String,
                        val turename : String,
                        val State : String,
                        val AddDate : String,
                        val UserEndDate : String?,
                        val PassEndDate : String?,
                        val UseTime1 : String?,
                        val UseTime2 : String?,
                        val RySSBM : String?,
                        val ErrNum : String?,
                        val isWork : String?,
                        val zjcx : String?,
                        val Bz01 : String?,
                        val Bz02 : String?,
                        val Bz03 : String?,
                        val qianming : String?
){
    constructor(
        InfoID: String,
        SfzInfoID: String,
        GongHao: String,
        UserName : String,
        PassWord : String,
        turename : String,
        State : String,
        AddDate : String,
    ) : this(
        InfoID,
        SfzInfoID,
        GongHao,
        UserName,
        PassWord,
        turename,
        State,
        AddDate,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}
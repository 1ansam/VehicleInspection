package com.yxf.vehicleinspection.bean

import java.util.*

/**
 *   author:yxf
 *   time:2021/9/27
 *   系统操作日志表(Tb_Log)
 *   @param id	自增编号	int	NOT NULL
 *   @param logname	名称	varchar(16)
 *   @param source	来源	varchar(128)
 *   @param recordtime	时间	smalldatetime
 *   @param eventid	分类编号	varchar(16)
 *   @param tasktype	日志类型	varchar(16)
 *   @param loglevel	日志等级	varchar(16)
 *   @param keywords	关键字	varchar(16)
 *   @param userinfo	用户信息	varchar(16)
 *   @param terminalip	终端地址	varchar(128)
 *   @param detailinformation	详细信息	varchar(MAX)

 */
data class Tb_Log(val id : Int,
                  val logname : String?,
                  val source : String?,
                  val recordtime : Date?,
                  val eventid : String?,
                  val tasktype : String?,
                  val loglevel : String?,
                  val keywords : String?,
                  val userinfo : String?,
                  val terminalip : String?,
                  val detailinformation : String?,
){
    constructor(
        id: Int,
    ) : this(
        id,
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
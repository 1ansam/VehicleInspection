package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   系统终端信息表(Tb_Terminal)
 *   id	自增编号	int	NOT NULL
ip	地址	varchar(32)	NOT NULL
name	名称	varchar(32)
isWork	是否有效	nvarchar(1)
Bz01	备注	varchar(32)
Bz02	备注	varchar(32)
Bz03	备注	varchar(32)

 */
data class Tb_Terminal(val id : String,
                       val ip : String,
                       val name : String?,
                       val isWork : String?,
                       val Bz01 : String?,
                       val Bz02 : String?,
                       val Bz03 : String?
){
    constructor(
        id: String,
        ip: String,
    ) : this(
        id,
        ip,
        null,
        null,
        null,
        null,
        null
    )
}
package com.yxf.vehicleinspection.bean

import java.util.*

/**
 *   author:yxf
 *   time:2021/9/27
 *   声级检验数据表(JcDate_N1)
 *   @param id	自增编号	int	NOT NULL
 *   @param Lsh	流水号	varchar(50)	NOT NULL
 *   @param hpzl	号牌种类	varchar(2)
 *   @param hphm	号牌号码	varchar(15)
 *   @param Jccs	检测次数	int	NOT NULL
 *   @param JcDate	检测日期	date
 *   @param KsTime	开始时间	varchar(32)
 *   @param JsTime	结束时间	varchar(32)
 *   @param JcPj	检测评价	varchar(2)
 *   @param DaLB	数据类别	varchar(2)
 *   @param Date_SJ	声级数据	varchar(8)
 *   @param Date_SJ_GB	声级评价标准	varchar(32)
 *   @param Bz1	备注	varchar(24)
 *   @param Bz2	备注	varchar(24)
 *   @param Bz3	备注	varchar(24)
 */
data class JcDate_N1(val id : Int,
                     val Lsh : String,
                     val hpzl : String?,
                     val hphm : String?,
                     val Jccs : Int,
                     val JcDate : Date?,
                     val KsTime : String?,
                     val JsTime : String?,
                     val JcPj : String?,
                     val DaLB : String?,
                     val Date_SJ : String?,
                     val Date_SJ_GB : String?,
                     val Bz1 : String?,
                     val Bz2 : String?,
                     val Bz3 : String?,


)
{
    constructor(
        id: Int,
        Lsh: String,
        Jccs: Int
    ) : this(
        id,
        Lsh,
        null,
        null,
        Jccs,
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
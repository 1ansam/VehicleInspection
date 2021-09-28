package com.yxf.vehicleinspection.bean

import java.util.*

/**
 *   author:yxf
 *   time:2021/9/27
 *   速度检验数据表(JcDate_S1)
 *   @param id	自增编号	int	NOT NULL
 *   @param Lsh	流水号	varchar(50)	NOT NULL
 *   @param hpzl	号牌种类	varchar(2)
 *   @param hphm	号牌号码	varchar(15)
 *   @param Jccs	检测次数	int	NOT NULL
 *   @param JcDate	检测日期	date
 *   @param KsTime	开始时间	varchar(32)
 *   @param JsTime	结束时间	varchar(32)
 *   @param JcPj	检测评价	varchar(2)
 *   @param DaLB	数据类别	varchar(2)	NOT NULL
 *   @param Date_SD	速度数据	varchar(8)
 *   @param Date_SD_GB	速度评价标准	varchar(32)
 *   @param data_pj_zj	综检评价	varchar(8)
 *   @param data_bz_zj	综检评价标准	varchar(32)
 *   @param Bz1	备注	varchar(24)
 *   @param Bz2	备注	varchar(24)
 *   @param Bz3	备注	varchar(24)

 */
data class JcDate_S1(val id : Int,
                     val Lsh : String,
                     val hpzl : String?,
                     val hphm : String?,
                     val Jccs : Int,
                     val JcDate : Date?,
                     val KsTime : String?,
                     val JsTime : String?,
                     val JcPj : String?,
                     val DaLB : String,
                     val Date_SD : String?,
                     val Date_SD_GB : String?,
                     val data_pj_zj : String?,
                     val data_bz_zj : String?,
                     val Bz1 : String?,
                     val Bz2 : String?,
                     val Bz3 : String?,


)
{
    constructor(
        id: Int,
        Lsh: String,
        Jccs: Int,
        DaLB: String,
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
        DaLB,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
    )
}
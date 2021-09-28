package com.yxf.vehicleinspection.bean

import java.util.*

/**
 *   author:yxf
 *   time:2021/9/27
 *   外廓尺寸数据表(JcDate_M1)
 *   @param id	自增编号	int	NOT NULL
 *   @param Lsh	流水号	varchar(50)	NOT NULL
 *   @param hpzl	号牌种类	varchar(2)
 *   @param hphm	号牌号码	varchar(15)
 *   @param Jccs	检测次数	int	NOT NULL
 *   @param JcDate	检测日期	date
 *   @param KsTime	开始时间	varchar(32)
 *   @param JsTime	结束时间	varchar(32)
 *   @param JcPj	检测评价	varchar(2)
 *   @param Date_WKCC	外廓尺寸数据	varchar(32)
 *   @param Date_WKCC_GB	外廓尺寸评价标准	varchar(32)
 *   @param Bz1	备注	varchar(24)
 *   @param Bz2	备注	varchar(24)
 *   @param Bz3	备注	varchar(24)
 *   @param Bz4	长范围
 *   @param Bz5	宽范围
 *   @param Bz6	高范围

 */
data class JcDate_M1(val id : String,
                     val Lsh : String,
                     val hpzl : String?,
                     val hphm : String?,
                     val Jccs : String,
                     val JcDate : String?,
                     val KsTime : String?,
                     val JsTime : String?,
                     val JcPj : String?,
                     val Date_WKCC : String?,
                     val Date_WKCC_GB : String?,
                     val Bz1 : String?,
                     val Bz2 : String?,
                     val Bz3 : String?,
                     val Bz4 : String?,
                     val Bz5 : String?,
                     val Bz6 : String?

)
{
    constructor(
        id: String,
        Lsh: String,
        Jccs: String
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
        null,
        null,
        null,
    )
}
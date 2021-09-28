package com.yxf.vehicleinspection.bean

import java.util.*

/**
 *   author:yxf
 *   time:2021/9/27
 *   悬架性检验数据表(JcDate_XJDate)
 *   @param id	自增编号	int	NOT NULL
 *   @param Lsh	流水号	varchar(50)	NOT NULL
 *   @param hpzl	号牌种类	varchar(2)
 *   @param hphm	号牌号码	varchar(15)
 *   @param Jccs	检测次数	int	NOT NULL
 *   @param JcDate	检测日期	date
 *   @param KsTime	开始时间	smalldatetime
 *   @param JsTime	结束时间	smalldatetime
 *   @param JcPj	检测评价	varchar(2)
 *   @param DaLB	数据类别	varchar(2)	NOT NULL
 *   @param weightL_jt	静态左轮重	varchar(8)
 *   @param weightR_jt	静态右轮重	varchar(8)
 *   @param weightL_minDt	动态最小左轮总	varchar(8)
 *   @param weightR_minDt	动态最小右轮重	varchar(8)
 *   @param xslL	左吸收率	varchar(8)
 *   @param xslL_pj	左吸收率评价	varchar(8)
 *   @param xslR	右吸收率	varchar(8)
 *   @param xslR_pj	右吸收率评价	varchar(8)
 *   @param xslZyc	吸收率左右差	varchar(8)
 *   @param xslZyc_pj	吸收率左右差评价	varchar(8)
 *   @param xslBz	吸收率标准	varchar(32)
 *   @param xlsZycBz	吸收率左右差标准	varchar(32)
 *   @param Bz1	备注	varchar(24)
 *   @param Bz2	备注	varchar(24)
 *   @param Bz3	备注	varchar(24)

 */
data class JcDate_XJDate(val id : Int,
                         val Lsh : String,
                         val hpzl : String?,
                         val hphm : String?,
                         val Jccs : Int,
                         val JcDate : Date?,
                         val KsTime : String?,
                         val JsTime : String?,
                         val JcPj : String?,
                         val DaLB : String,
                         val weightL_jt : String?,
                         val weightR_jt : String?,
                         val weightL_minDt : String?,
                         val weightR_minDt : String?,
                         val xslL : String?,
                         val xslL_pj : String?,
                         val xslR : String?,
                         val xslR_pj : String?,
                         val xslZyc : String?,
                         val xslZyc_pj : String?,
                         val xslBz : String?,
                         val xlsZycBz : String?,
                         val Bz1 : String?,
                         val Bz2 : String?,
                         val Bz3 : String?,
)
{
    constructor(
        id: Int,
        Lsh: String,
        Jccs: Int,
        DaLB: String
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
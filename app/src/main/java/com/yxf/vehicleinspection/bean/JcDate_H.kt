package com.yxf.vehicleinspection.bean

import java.util.*

/**
 *   author:yxf
 *   time:2021/9/27
 *   灯光数据表(JcDate_H)
 *   @param id	自增编号	int	NOT NULL
 *   @param Lsh	流水号	varchar(50)	NOT NULL
 *   @param hpzl	号牌种类	varchar(2)
 *   @param hphm	号牌号码	varchar(15)
 *   @param Jccs	检测次数	int	NOT NULL
 *   @param JcDate	检测日期	date
 *   @param KsTime	开始时间	varchar(32)
 *   @param JsTime	结束时间	varchar(32)
 *   @param JcPj	检测评价	varchar(2)
 *   @param DaLB	数据类型	varchar(2)
 *   @param Dg_F_GD	远光高度	varchar(8)
 *   @param DG_F_GQ	远光光强	varchar(8)
 *   @param DG_F_GQ_PJ	远光光强评价	varchar(8)
 *   @param DG_F_GQ_GB	远光光强评价标准	varchar(32)
 *   @param DG_F_SP	远光水平评偏移	varchar(8)
 *   @param DG_F_SP_PJ	远光水平偏移评价	varchar(8)
 *   @param DG_F_SP_GB	远光水平偏移评价标准	varchar(32)
 *   @param DG_F_CZ	远光垂直偏移	varchar(8)
 *   @param DG_F_CZPCZ	远光垂直偏差值	varchar(8)
 *   @param DG_F_CZ_PJ	远光垂直评价	varchar(8)
 *   @param DG_F_CZ_GB	远光垂直评价标准	varchar(32)
 *   @param DG_N_DG	近光灯高	varchar(8)
 *   @param DG_N_GQ	近光光强	varchar(8)
 *   @param DG_N_GQ_PJ	近光光强评价	varchar(8)
 *   @param DG_N_GQ_GB	近光光强评价标准	varchar(32)
 *   @param DG_N_SP	近光水平偏移	varchar(8)varchar(8)
 *   @param DG_N_SP_PJ	近光水评评价	varchar(8)
 *   @param DG_N_SP_GB	近光水评评价标准	varchar(32)
 *   @param DG_N_CZ	近光垂直	varchar(8)
 *   @param DG_N_CZPCZ	近光垂直偏差值	varchar(8)
 *   @param DG_N_CZ_PJ	近光垂直评价	varchar(8)
 *   @param DG_N_CZ_GB	近光垂直评价标准	varchar(32)
 *   @param DG_PJ	灯光评价	varchar(8)
 *   @param yggq_pj_zj	综检远光光强评价	varchar(8)
 *   @param yggq_bz_zj	综检远光光强评价标准	varchar(32)
 *   @param ygcz_pj_zj	远光垂直评价综检	varchar(8)
 *   @param ygcz_bz_zj	远光垂直评价综检标准	varchar(32)
 *   @param jgcz_pj_zj	近光垂直评价综检	varchar(8)
 *   @param jgcz_bz_zj	近光垂直评价标准综检	varchar(32)
 *   @param zpj_zj	总评价综检	varchar(8)
 *   @param Bz1	备注	varchar(24)
 *   @param Bz2	备注	varchar(24)
 *   @param Bz3	备注	varchar(24)

 */
data class JcDate_H(val id : String,
                    val Lsh : String,
                    val hpzl : String?,
                    val hphm : String?,
                    val Jccs : String,
                    val JcDate : String?,
                    val KsTime : String?,
                    val JsTime : String?,
                    val JcPj : String?,
                    val DaLB : String?,
                    val Dg_F_GD : String?,
                    val DG_F_GQ : String?,
                    val DG_F_GQ_PJ : String?,
                    val DG_F_GQ_GB : String?,
                    val DG_F_SP : String?,
                    val DG_F_SP_PJ : String?,
                    val DG_F_SP_GB : String?,
                    val DG_F_CZ : String?,
                    val DG_F_CZPCZ : String?,
                    val DG_F_CZ_PJ : String?,
                    val DG_F_CZ_GB : String?,
                    val DG_N_DG : String?,
                    val DG_N_GQ : String?,
                    val DG_N_GQ_PJ : String?,
                    val DG_N_GQ_GB : String?,
                    val DG_N_SP : String?,
                    val DG_N_SP_PJ : String?,
                    val DG_N_SP_GB : String?,
                    val DG_N_CZ : String?,
                    val DG_N_CZPCZ : String?,
                    val DG_N_CZ_PJ : String?,
                    val DG_N_CZ_GB : String?,
                    val DG_PJ : String?,
                    val yggq_pj_zj : String?,
                    val yggq_bz_zj : String?,
                    val ygcz_pj_zj : String?,
                    val ygcz_bz_zj : String?,
                    val jgcz_pj_zj : String?,
                    val jgcz_bz_zj : String?,
                    val zpj_zj : String?,
                    val Bz1 : String?,
                    val Bz2 : String?,
                    val Bz3 : String?,
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
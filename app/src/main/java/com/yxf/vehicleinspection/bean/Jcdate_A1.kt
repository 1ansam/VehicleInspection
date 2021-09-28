package com.yxf.vehicleinspection.bean

import java.util.*

/**
 *   author:yxf
 *   time:2021/9/27
 *   侧滑项目检验数据表(Jcdate_A1)
 *   @param ID	数据编号，自增量	int	NOT NULL
 *   @param Lsh	机动车序号	varchar(17)
 *   @param hpzl	号牌种类	varchar(2)
 *   @param hphm	号牌号码	varchar(15)
 *   @param jccs	检测次数	int
 *   @param jcdate	检测时间	date
 *   @param kstime	开始时间	varchar(32)
 *   @param jstime	结束时间	varchar(32)
 *   @param jcpj	检测评价	varchar(2)
 *   @param dalb	数据类别	varchar(2)
 *   @param Date_ch	侧滑数据	varchar(8)
 *   @param Data_chpj	侧滑评价	varchar(2)
 *   @param Date_ch_gb	侧滑评价标准	varchar(32)
 *   @param Data_ch_two	第二转向轴侧滑量	varchar(8)
 *   @param Data_chpj_two	第二转向轴侧滑评价	varchar(2)
 *   @param One_bz_zj	侧滑综检评价标准	varchar(32)
 *   @param One_pj_zj	侧滑综检评价	varchar(8)
 *   @param Zpj_zj	侧滑总评价综检	varchar(8)
 *   @param Bz1	备注	varchar(24)
 *   @param Bz2	备注	varchar(24)
 *   @param Bz3	备注	varchar(24)

 */
data class Jcdate_A1(val ID : Int,
                     val Lsh : String?,
                     val hpzl : String?,
                     val hphm : String?,
                     val jccs : Int?,
                     val jcdate : Date?,
                     val kstime : String?,
                     val jstime : String?,
                     val jcpj : String?,
                     val dalb : String?,
                     val Date_ch : String?,
                     val Data_chpj : String?,
                     val Date_ch_gb : String?,
                     val Data_ch_two : String?,
                     val Data_chpj_two : String?,
                     val One_bz_zj : String?,
                     val One_pj_zj : String?,
                     val Zpj_zj : String?,
                     val Bz1 : String?,
                     val Bz2 : String?,
                     val Bz3 : String?
)
{
    constructor(
        id : Int
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
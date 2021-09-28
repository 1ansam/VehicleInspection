package com.yxf.vehicleinspection.bean

import java.util.*

/**
 *   author:yxf
 *   time:2021/9/27
 *   机动车路试检验数据表(JcData_R)
 *   @param id	自增编号	int	Not nul
 *   @param lsh	流水号	varchar(32)	Not null
 *   @param hpzl	号牌种类	varchar(2)
 *   @param hphm	号牌号码	varchar(8)
 *   @param jccs	检测次数	int
 *   @param dalb	数据类别（R1,R2,R3）	varchar(8)
 *   @param jcdate	检测日期	date
 *   @param kstime	开始时间	varchar(32)
 *   @param jstime	结束时间	varchar(32)
 *   @param jcpj	检测评价	varchar(2)
 *   @param data01	路试通道宽度（R2时为驻车坡度，R3时为路试车速）	varchar(8)
 *   @param data02	路试初速度（R2时为驻车时长）	varchar(8)
 *   @param data03	制动距离（R2时为驻车情况）	varchar(8)
 *   @param data04	协调时间	varchar(8)
 *   @param data05	MFDD	varchar(8)
 *   @param data06	制动距离评价标准	varchar(8)
 *   @param data07	MFDD评价标准	varchar(8)
 *   @param data08	制动稳定性	varchar(8)
 *   @param data09	制动距离检验结论	varchar(8)
 *   @param Data10	协调时间检验结论	varchar(8)
 *   @param Data11	协调时间评价标准	varchar(8)
 *   @param Data12	MFDD检验结论	varchar(8)
 *   @param Data13	MFDD综检评价标准	varchar(8)
 *   @param Data14	MFDD综检检验结论	varchar(8)
 *   @param Data15	协调时间综检评价标准	varchar(8)
 *   @param Data16	协调时间综检检验结论	varchar(8)
 *   @param Data17	制动距离综检评价标准	varchar(8)
 *   @param Data18	制动距离综检检验结论	varchar(8)
 *   @param Data19	路试通道宽度综检	varchar(8)
 *   @param Data20	路试初速度综检	varchar(8)
 *   @param Bz1	备注	varchar(8)
 *   @param Bz2	备注	varchar(8)

 */
data class JcData_R (val id : String,
                     val lsh : String,
                     val hpzl : String?,
                     val hphm : String?,
                     val jccs : String?,
                     val dalb : String?,
                     val jcdate : String?,
                     val kstime : String?,
                     val jstime : String?,
                     val jcpj : String?,
                     val data01 : String?,
                     val data02 : String?,
                     val data03 : String?,
                     val data04 : String?,
                     val data05 : String?,
                     val data06 : String?,
                     val data07 : String?,
                     val data08 : String?,
                     val data09 : String?,
                     val Data10 : String?,
                     val Data11 : String?,
                     val Data12 : String?,
                     val Data13 : String?,
                     val Data14 : String?,
                     val Data15 : String?,
                     val Data16 : String?,
                     val Data17 : String?,
                     val Data18 : String?,
                     val Data19 : String?,
                     val Data20 : String?,
                     val Bz1 : String?,
                     val Bz2 : String?,
)
{
    constructor(
        id: String,
        lsh: String,
    ) : this(
        id,
        lsh,
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
package com.yxf.vehicleinspection.bean

import java.util.*

/**
 *   author:yxf
 *   time:2021/9/27
 *   检验结果流水表(QcyJcDateFlow)
 *   @param InfoID	自增编号	int	NOT NULL
 *   @param Lsh	流水号	varchar(32)	NOT NULL
 *   @param ajywlb	安检业务类别	varchar(8)
 *   @param ajjccs	安检检测次数	int
 *   @param zjywlb	综检业务类别	varchar(8)
 *   @param zjjccs	综检检测次数	int
 *   @param hjywlb	环检业务类别	varchar(8)
 *   @param hjjccs	环检检测次数	int
 *   @param Jccs	检测次数	int	 NOT NULL
 *   @param Jcxm	检验项目	varchar(128)
 *   @param ajjcxm	安检检测项目	varchar(128)
 *   @param zjjcxm	综检检测项目	varchar(128)
 *   @param hjjcxm	环检检测项目	varchar(128)
 *   @param Jcrq	检测日期	date
 *   @param JcTime	检测时间	sql_variant
 *   @param JcKsTime	开始时间	smalldatetime
 *   @param JcJsTime	结束时间	smalldatetime
 *   @param JcXH	检测线号	varchar(4)
 *   @param jcpj	检测评价	varchar(8)
 *   @param JcJg	检测结果	varchar(24)
 *   @param Ry_01	人员1	varchar(12)
 *   @param Ry_02	人员2	varchar(12)
 *   @param Ry_03	人员3	varchar(12)
 *   @param Ry_04	人员4	varchar(12)
 *   @param Ry_05	人员5	varchar(12)
 *   @param Ry_06	人员6	varchar(12)
 *   @param Ry_07	人员7	varchar(12)
 *   @param zjlsh	综检流水号	varchar(50)
 *   @param Bz1	备注	varchar(24)
 *   @param Bz2	备注	varchar(24)
 *   @param Bz3	备注	varchar(24)
 *   @param jyxmstatus	所有检验项目状态
 *   @param jyxmjg	所有检验项目结果
 *   @param printSheet	仪器设备检验表打印
 *   @param printCard	报告单打印
 *   @param WjPath	外检单地址
 *   @param SheetPath	仪器设别检验表地址
 *   @param CardPath	报告单地址
 *   @param printWj	外检单打印
 *   @param jczt	检测流水状态
 *   @param shzt	审核状态

 */
data class QcyJcDateFlow(val InfoID : String,
                         val Lsh : String,
                         val ajywlb : String?,
                         val ajjccs : String?,
                         val zjywlb : String?,
                         val zjjccs : String?,
                         val hjywlb : String?,
                         val hjjccs : String?,
                         val Jccs : String,
                         val Jcxm : String?,
                         val ajjcxm : String?,
                         val zjjcxm : String?,
                         val hjjcxm : String?,
                         val Jcrq : String?,
                         val JcTime : String?,
                         val JcKsTime : String?,
                         val JcJsTime : String?,
                         val JcXH : String?,
                         val jcpj : String?,
                         val JcJg : String?,
                         val Ry_01 : String?,
                         val Ry_02 : String?,
                         val Ry_03 : String?,
                         val Ry_04 : String?,
                         val Ry_05 : String?,
                         val Ry_06 : String?,
                         val Ry_07 : String?,
                         val zjlsh : String?,
                         val Bz1 : String?,
                         val Bz2 : String?,
                         val Bz3 : String?,
                         val jyxmstatus : String?,
                         val jyxmjg : String?,
                         val printSheet : String?,
                         val printCard : String?,
                         val WjPath : String?,
                         val SheetPath : String?,
                         val CardPath : String?,
                         val printWj : String?,
                         val jczt : String?,
                         val shzt : String?
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
        null,
        null,
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
    )
}
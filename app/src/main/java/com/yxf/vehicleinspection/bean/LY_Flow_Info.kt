package com.yxf.vehicleinspection.bean

import java.sql.Date
import java.sql.Timestamp

/**
 *   author:yxf
 *   time:2021/9/27
 *   检测过程控制表(LY_Flow_Info)
 *   @param ID	自增编号	int	NOT NULL
 *   @param Lsh	流水号	varchar(32)	NOT NULL
 *   @param ajywlb	安检业务类别	varchar(8)
 *   @param ajjccs	安检检测次数	int
 *   @param zjywlb	综检业务类别	varchar(8)
 *   @param zjjccs	综检检测次数	int
 *   @param hjywlb	环检业务类别	varchar(8)
 *   @param hjjccs	环检检测次数	int
 *   @param Jccs	检测次数	int	 NOT NULL
 *   @param AddRq	添加日期	smalldatetime
 *   @param JcXm	检测项目	varchar(128)
 *   @param ajjcxm	安检检测项目	varchar(128)
 *   @param zjjcxm	综检检测项目	varchar(128)
 *   @param hjjcxm	环检检测项目	varchar(128)
 *   @param JcJg	检测结果	varchar(128)
 *   @param Jc_S	检测状态	varchar(128)
 *   @param Ry_01	人员1	varchar(12)
 *   @param Ry_02	人员2	varchar(12)
 *   @param Ry_03	人员3	varchar(12)
 *   @param Ry_04	人员4	varchar(12)
 *   @param Ry_05	人员5	varchar(12)
 *   @param Ry_06	人员6	varchar(12)
 *   @param Ry_07	人员7	varchar(12)
 *   @param GW_01	工位1	varchar(2)
 *   @param GW_02	工位2	varchar(2)
 *   @param GW_03	工位3	varchar(2)
 *   @param GW_04	工位4	varchar(2)
 *   @param GW_05	工位5	varchar(2)
 *   @param GW_06	工位6	varchar(2)
 *   @param GW_07	工位7	varchar(2)
 *   @param GW_08	工位8	varchar(2)
 *   @param GW_09	工位9	varchar(2)
 *   @param GW_10	工位10	varchar(2)
 *   @param GW_11	工位11	varchar(2)
 *   @param GW_12	工位12	varchar(2)
 *   @param wgstatus	外观状态	varchar(2)
 *   @param dpstatus	底盘状态	varchar(2)
 *   @param dpdtstatus	底盘动态状态	varchar(2)
 *   @param wkccstatus	外廓尺寸状态	varchar(2)
 *   @param lszdstatus	路试制动状态	varchar(2)
 *   @param Man_TD_WG	外观通道	varchar(8)
 *   @param Man_TD_DP	底盘通道	varchar(8)
 *   @param Man_TD_DT	动态通道	varchar(8)
 *   @param Man_TD_LS	路试通道	varchar(8)
 *   @param SB_TD	设备调试	varchar(8)
 *   @param isonline	是否上线	varchar(2)
 *   @param ZZCC	轴重重测	varchar(2)
 *   @param zjlsh	综检流水号	varchar(50)
 *   @param sxsj	上线时间	datetime
 *   @param Bz1	备注	varchar(128)
 *   @param Bz2	备注	varchar(128)
 *   @param Bz3	备注	varchar(128)
 *   @param zbzlstatus	整备质量状态
 *   @param onlinetime	上线时间
 *   @param lwcxstatus	联网查询状态
 *   @param wyxjcstatus	唯一性检查状态
 *   @param jyxmstatus	所有检验项目状态
 *   @param jyxmjg	所有检验项目结果
 *   @param jczt	检验流水状态

 */
data class LY_Flow_Info(val ID : Int,
                        val Lsh : String,
                        val ajywlb : String?,
                        val ajjccs : Int?,
                        val zjywlb : String?,
                        val zjjccs : Int?,
                        val hjywlb : String?,
                        val hjjccs : Int?,
                        val Jccs : Int,
                        val AddRq : Date,
                        val JcXm : String?,
                        val ajjcxm : String?,
                        val zjjcxm : String?,
                        val hjjcxm : String?,
                        val JcJg : String?,
                        val Jc_S : String?,
                        val Ry_01 : String?,
                        val Ry_02 : String?,
                        val Ry_03 : String?,
                        val Ry_04 : String?,
                        val Ry_05 : String?,
                        val Ry_06 : String?,
                        val Ry_07 : String?,
                        val GW_01 : String?,
                        val GW_02 : String?,
                        val GW_03 : String?,
                        val GW_04 : String?,
                        val GW_05 : String?,
                        val GW_06 : String?,
                        val GW_07 : String?,
                        val GW_08 : String?,
                        val GW_09 : String?,
                        val GW_10 : String?,
                        val GW_11 : String?,
                        val GW_12 : String?,
                        val wgstatus : String?,
                        val dpstatus : String?,
                        val dpdtstatus : String?,
                        val wkccstatus : String?,
                        val lszdstatus : String?,
                        val Man_TD_WG : String?,
                        val Man_TD_DP : String?,
                        val Man_TD_DT : String?,
                        val Man_TD_LS : String?,
                        val SB_TD : String?,
                        val isonline : String?,
                        val ZZCC : String?,
                        val zjlsh : String?,
                        val sxsj : String?,
                        val Bz1 : String?,
                        val Bz2 : String?,
                        val Bz3 : String?,
                        val zbzlstatus : String?,
                        val onlinetime : String?,
                        val lwcxstatus : String?,
                        val wyxjcstatus : String?,
                        val jyxmstatus : String?,
                        val jyxmjg : String?,
                        val jczt : String?
)
{
    constructor(
        id: Int,
        Lsh: String,
        Jccs: Int,
        AddRq: Date
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
        AddRq,
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
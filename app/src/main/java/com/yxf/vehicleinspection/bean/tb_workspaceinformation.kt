package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   检测工位参数信息表(tb_workspaceinformation)
 *   @param id	自增编号	int	NOT NULL
 *   @param jcgw	检验工位	varchar(8)
 *   @param jcxh	检测线代号	varchar(8)
 *   @param ip	Ip地址	varchar(24)
 *   @param pborgt	平板或滚筒线	varchar(2)
 *   @param czzb	存在主板	varchar(1)
 *   @param zbtd	主板通道	varchar(2)
 *   @param czxsp	存在显示屏	varchar(1)
 *   @param xsplx	显示屏类型	varchar(8)
 *   @param xsptd1	显示屏通道1	varchar(2)
 *   @param xsptd2	显示屏通道2	varchar(2)
 *   @param xspshxs	显示屏上行显示	varchar(200)
 *   @param xspxhxs	显示屏下行显示	varchar(200)
 *   @param czcs	存在车速	varchar(1)
 *   @param cstdDw	车速到位通道	varchar(2)
 *   @param cstdZs	车速转速通道	varchar(2)
 *   @param cstdYk	车速遥控通道	varchar(2)
 *   @param cstdjs	车速举升通道	varchar(2)
 *   @param cstdxj	车速下降通道	varchar(2)
 *   @param czzz	存在轴重	varchar(1)
 *   @param zztddw	轴重到位通道	varchar(2)
 *   @param zztdzlz	轴重左轮重通道	varchar(2)
 *   @param zztdylz	轴重右轮重通道	varchar(2)
 *   @param czzd	存在制动	varchar(1)
 *   @param dataZd1	制动工位信息1	varchar(2)
 *   @param dataZd2	制动参数2	varchar(2)
 *   @param dataZd3	制动参数3	varchar(2)
 *   @param dataZd4	制动参数4	varchar(2)
 *   @param dataZd5	制动参数5	varchar(2)
 *   @param dataZd6	制动参数6	varchar(2)
 *   @param dataZd7	制动参数7	varchar(2)
 *   @param dataZd8	制动参数8	varchar(2)
 *   @param dataZd9	制动参数9	varchar(2)
 *   @param dataZd10	制动参数10	varchar(2)
 *   @param dataZd11	制动参数11	varchar(2)
 *   @param dataZd12	制动参数12	varchar(2)
 *   @param dataZd13	制动参数13	varchar(2)
 *   @param dataZd14	制动参数14	varchar(2)
 *   @param dataZd15	制动参数15	varchar(2)
 *   @param dataZd16	制动参数16	varchar(2)
 *   @param czxj	存在悬架	varchar(1)
 *   @param xjtdzdj	悬架左电机通道	varchar(2)
 *   @param xjtdydj	悬架右电机通道	varchar(2)
 *   @param xjtdzlz	悬架通道左轮重	varchar(2)
 *   @param xjtdylz	悬架通道右轮重	varchar(2)
 *   @param xjtddw	悬架到位通道	varchar(2)
 *   @param czdg	存在灯光	varchar(1)
 *   @param dgyxh	灯光仪型号	varchar(8)
 *   @param dgyclfs	灯光仪测量方式	varchar(8)
 *   @param dgytkw	灯光仪停靠位	varchar(8)
 *   @param dgydwtd1	灯光仪到位通道1	varchar(2)
 *   @param dgydwtd2	灯光仪到位通道2	varchar(2)
 *   @param dgysjtd1	灯光仪数据通道1	varchar(2)
 *   @param dgysjtd2	灯光仪数据通道2	varchar(2)
 *   @param czch	存在侧滑	varchar(1)
 *   @param chtdsj	侧滑数据通道	varchar(2)
 *   @param chtddw	侧滑到位通道	varchar(2)
 *   @param chtdlk	侧滑离开通道	varchar(2)
 *   @param czdp	存在底盘	varchar(1)
 *   @param dpxjp	底盘小键盘	varchar(2)
 *   @param czsj	存在声级	varchar(1)
 *   @param sjtd	声级通道	varchar(2)
 *   @param czdata	存在采集模块	varchar(1)
 *   @param dataIP	采集模块IP	varchar(50)
 *   @param czyd	存在烟度	varchar(1)
 *   @param ydtxdk	烟度通讯端口	varchar(2)
 *   @param czfq	存在废气	varchar(1)
 *   @param fqtxdk	废气通讯端口	varchar(2)
 *   @param czdlx	存在动力性	varchar(1)
 *   @param sgtorlgt	四滚筒或六滚筒	varchar(2)
 *   @param dlxtxdk	动力性通讯端口	varchar(2)
 *   @param czjjx	存在经济型	varchar(1)
 *   @param jjxtxdk	经济性通讯端口	varchar(2)
 *   @param cssycgj	车速使用测功机	varchar(2)
 *   @param dataType	数据采集方式

 */
data class tb_workspaceinformation(val id : String,
                              val jcgw : String?,
                              val jcxh : String?,
                              val ip : String?,
                              val pborgt : String?,
                              val czzb : String?,
                              val zbtd : String?,
                              val czxsp : String?,
                              val xsplx : String?,
                              val xsptd1 : String?,
                              val xsptd2 : String?,
                              val xspshxs : String?,
                              val xspxhxs : String?,
                              val czcs : String?,
                              val cstdDw : String?,
                              val cstdZs : String?,
                              val cstdYk : String?,
                              val cstdjs : String?,
                              val cstdxj : String?,
                              val czzz : String?,
                              val zztddw : String?,
                              val zztdzlz : String?,
                              val zztdylz : String?,
                              val czzd : String?,
                              val dataZd1 : String?,
                              val dataZd2 : String?,
                              val dataZd3 : String?,
                              val dataZd4 : String?,
                              val dataZd5 : String?,
                              val dataZd6 : String?,
                              val dataZd7 : String?,
                              val dataZd8 : String?,
                              val dataZd9 : String?,
                              val dataZd10 : String?,
                              val dataZd11 : String?,
                              val dataZd12 : String?,
                              val dataZd13 : String?,
                              val dataZd14 : String?,
                              val dataZd15 : String?,
                              val dataZd16 : String?,
                              val czxj : String?,
                              val xjtdzdj : String?,
                              val xjtdydj : String?,
                              val xjtdzlz : String?,
                              val xjtdylz : String?,
                              val xjtddw : String?,
                              val czdg : String?,
                              val dgyxh : String?,
                              val dgyclfs : String?,
                              val dgytkw : String?,
                              val dgydwtd1 : String?,
                              val dgydwtd2 : String?,
                              val dgysjtd1 : String?,
                              val dgysjtd2 : String?,
                              val czch : String?,
                              val chtdsj : String?,
                              val chtddw : String?,
                              val chtdlk : String?,
                              val czdp : String?,
                              val dpxjp : String?,
                              val czsj : String?,
                              val sjtd : String?,
                              val czdata : String?,
                              val dataIP : String?,
                              val czyd : String?,
                              val ydtxdk : String?,
                              val czfq : String?,
                              val fqtxdk : String?,
                              val czdlx : String?,
                              val sgtorlgt : String?,
                              val dlxtxdk : String?,
                              val czjjx : String?,
                              val jjxtxdk : String?,
                              val cssycgj : String?,
                              val dataType : String?
){
    constructor(
        id: String,
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
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}
package com.yxf.vehicleinspection.bean

/**
 *   @author:yxf
 *   @time:2021/9/27
 *   @param id	自增编号	int	NOT NULL
 *   @param jcsjyxq	检测数据有效期	int
 *   @param jcsjbcnx	检测数据保存年限	nchar(10)
 *   @param dysjfs	打印数据方式	varchar(2)
 *   @param dycffs	打印出发方式	varchar(2)
 *   @param zkddz	未使用	varchar(128)
 *   @param BsOK	合格标志	varchar(12)
 *   @param BsNo	不合格标志	varchar(12)
 *   @param BsDefalue	默认标志	varchar(12)
 *   @param BsWx	无效标志	varchar(12)
 *   @param Web_Udl	监管业务联网地址	varchar(128)
 *   @param WEB_Work	未使用	varchar(128)
 *   @param Web_Pass	业务序列号	varchar(128)
 *   @param Web_Pass_Move	移动端序列号	varchar(128)
 *   @param Web_Pass_Video	视屏序列号	varchar(128)
 *   @param Web_Cjdw	承建单位	varchar(128)
 *   @param WEB_Pic	拍照地址	varchar(128)
 *   @param WEB_Receipt	未使用	varchar(128)
 *   @param WEB_Info01	备用	varchar(128)
 *   @param WEB_Info02	备用	varchar(128)
 *   @param WEB_Info03	备用	varchar(128)
 *   @param WEB_Info04	备用	varchar(128)
 *   @param sfpz	是否拍照	varchar(1)
 *   @param zpbcdz	照片保存地址	varchar(128)
 *   @param Dw_Xkzh	单位许可证号	varchar(32)
 *   @param Dw_Dhhm	电话号码	varchar(32)
 *   @param Dw_mc	单位名称	varchar(128)
 *   @param jyjgbh	检验机构编号	varchar(32)
 *   @param Dw_dz	单位地址	varchar(200)
 *   @param Report_Head	报表头	varchar(48)
 *   @param Report_BZ	报表尾	varchar(250)
 *   @param controlIP	总控地址	varchar(24)
 *   @param controlPort	总控端口	varchar(24)
 *   @param controlType	总控类型	varchar(8)
 *   @param jcfs	检测方式	varchar(8)
 *   @param jyywlwfs	检验业务联网方式	varchar(8)
 *   @param Web_Udl_jt	综检业务联网地址	varchar(128)
 *   @param Web_Pass_jt	综检业务联网验证码	varchar(128)
 *   @param fwcs	范围车速	varchar(8)
 *   @param zdlph	制动率平衡	varchar(8)
 *   @param dgbpc	灯光偏差	varchar(8)
 *   @param zxl	阻滞率	varchar(8)
 *   @param sjxm	声级项目	varchar(8)
 *   @param dbgl	达标功率	varchar(8)
 *   @param sdcs	速度次数	int
 *   @param Bz1	备注	varchar(128)
 *   @param Bz2	备注	varchar(128)
 *   @param Bz3	备注	varchar(128)
 *   @param Web_VideoRecordPath	录像存放虚拟地址
 *   @param Web_AjReportPath	安检报告单地址
 *   @param Web_HbReportPath	环检报告单地址
 *   @param SFF	是否收费功能
 *   @param KPF	是否开票功能
 *   @param appointmentURL	预约接口地址

 */
data class SystermCs_All(val id : String,
                         val jcsjyxq : String?,
                         val jcsjbcnx : String?,
                         val dysjfs : String?,
                         val dycffs : String?,
                         val zkddz : String?,
                         val BsOK : String?,
                         val BsNo : String?,
                         val BsDefalue : String?,
                         val BsWx : String?,
                         val Web_Udl : String?,
                         val WEB_Work : String?,
                         val Web_Pass : String?,
                         val Web_Pass_Move : String?,
                         val Web_Pass_Video : String?,
                         val Web_Cjdw : String?,
                         val WEB_Pic : String?,
                         val WEB_Receipt : String?,
                         val WEB_Info01 : String?,
                         val WEB_Info02 : String?,
                         val WEB_Info03 : String?,
                         val WEB_Info04 : String?,
                         val sfpz : String?,
                         val zpbcdz : String?,
                         val Dw_Xkzh : String?,
                         val Dw_Dhhm : String?,
                         val Dw_mc : String?,
                         val jyjgbh : String?,
                         val Dw_dz : String?,
                         val Report_Head : String?,
                         val Report_BZ : String?,
                         val controlIP : String?,
                         val controlPort : String?,
                         val controlType : String?,
                         val jcfs : String?,
                         val jyywlwfs : String?,
                         val Web_Udl_jt : String?,
                         val Web_Pass_jt : String?,
                         val fwcs : String?,
                         val zdlph : String?,
                         val dgbpc : String?,
                         val zxl : String?,
                         val sjxm : String?,
                         val dbgl : String?,
                         val sdcs : Int?,
                         val Bz1 : String?,
                         val Bz2 : String?,
                         val Bz3 : String?,
                         val Web_VideoRecordPath : String?,
                         val Web_AjReportPath : String?,
                         val Web_HbReportPath : String?,
                         val SFF : String?,
                         val KPF : String?,
                         val appointmentURL : String?,


)
{
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
        null
    )
}
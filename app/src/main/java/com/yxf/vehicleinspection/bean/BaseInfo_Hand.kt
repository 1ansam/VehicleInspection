package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   机动车基本信息表1(BaseInfo_Hand)
 *   @param id	自增编号	Int	Not null
 *   @param Lsh	流水号	Varchar(32)	Not null
 *   @param hpzl	号牌种类	varchar(2)
 *   @param hphm	号牌号码	varchar(15)
 *   @param QZDZ	前照灯制	varchar(3)
 *   @param QDFS	驱动方式	varchar(32)
 *   @param ZXJFS	转向轴方式	varchar(2)
 *   @param DGTZFS	灯光调整方式	varchar(2)
 *   @param SYCH	是否三元催化	varchar(2)
 *   @param SFKZ	是否空载	varchar(2)
 *   @param XSLC	行驶里程	varchar(12)
 *   @param Max_SD	最大设计车速	varchar(8)
 *   @param ZJDW	直接档位	varchar(16)
 *   @param BSQXS	变速器形式	varchar(16)
 *   @param ZDFS	制动方式	varchar(16)
 *   @param WRZY	涡轮增压	varchar(2)
 *   @param QGS	气缸数	varchar(8)
 *   @param RyGG	燃油规格	varchar(32)
 *   @param WZDW	维修单位	varchar(128)
 *   @param JGRQ	竣工日期	varchar(32)
 *   @param Lsdh	联系电话	varchar(18)
 *   @param Lxdz	联系地址	varchar(200)
 *   @param Aj_Veh_Type	安检车型	varchar(8)
 *   @param Hj_Veh_Type	环检车型	varchar(8)
 *   @param Zj_Veh_Type	综检车型	varchar(8)
 *   @param hpys	号牌颜色	varchar(8)
 *   @param qdzhw	驱动轴位	varchar(8)
 *   @param qdzhsh	驱动轴数	int
 *   @param zhzhsh	主轴数	int
 *   @param zhchzhw	驻车轴位	varchar(8)
 *   @param edzhs	额定转速	varchar(8)
 *   @param ajywlb	安检业务类别	varchar(8)
 *   @param zjywlb	综检业务类别	varchar(8)
 *   @param hjywlb	环检业务类别	varchar(8)
 *   @param gb	国标车型	varchar(8)
 *   @param jdchsshlb	机动车所属类别	varchar(8)
 *   @param jcxlb	检测线类别	varchar(8)
 *   @param qzhsh	前轴数	int
 *   @param zhchzhsh	驻车轴数	int
 *   @param wgchx	外观车型	varchar(8)
 *   @param chych	乘用车	varchar(8)
 *   @param ednj	额定扭矩	varchar(8)
 *   @param ednjzhs	额定扭矩转速	varchar(8)
 *   @param glbzhfsh	功率表征方式	varchar(8)
 *   @param kchdj	客车等级	varchar(8)
 *   @param hchchshxsh	货车车身形式	varchar(8)
 *   @param qdzhzhl	驱动轴质量	varchar(8)
 *   @param bzhzhw	并装轴位	varchar(8)
 *   @param zhxzhsh	转向轴数	varchar(8)
 *   @param zjjylb	综检检验类别	varchar(8)
 *   @param yyzhh	营运证号	varchar(24)
 *   @param Info_01	备用数据位1	varchar(16)
 *   @param Info_02	备用数据	varchar(16)
 *   @param Info_03	备用数据	varchar(16)
 *   @param Info_04	备用数据	varchar(16)
 *   @param Info_05	备用数据	varchar(16)
 *   @param Info_06	备用数据	varchar(16)
 *   @param Info_07	备用数据	varchar(16)
 *   @param Info_08	备用数据	varchar(16)
 *   @param Info_09	备用数据	varchar(16)
 *   @param Info_10	备用数据	varchar(16)
 *   @param zjlsh	综检流水号	varchar(50)
 *   @param Bz01	备注	varchar(32)
 *   @param Bz02	备注	varchar(32)
 *   @param Bz03	备注	varchar(32)
 *   @param sfjf	是否缴费	varchar(2)
 *   @param sfkp	是否开票	varchar(2)
 *   @param tb	是否退办	varchar(2)
 *   @param sfgp	是否高拍上传	varchar(2)
 *   @param dzss	是否电子手刹	varchar(2)
 *   @param kqxjzw	空气悬架轴位	varchar(8)
 *   @param zhchlsh	主车流水号（挂车时填写）	varchar(24)
 *   @param hjxm	环检项目	varchar(24)
 *   @param sjr	送检人	varchar(20)
 *   @param sjrsfzh	送检人身份证号	varchar(50)
 *   @param sjrdh	送检人电话	varchar(20)

 */
data class BaseInfo_Hand(val id : String,
                    val Lsh : String,
                    val hpzl: String?,
                    val hphm: String?,
                    val QZDZ: String?,
                    val QDFS: String?,
                    val ZXJFS: String?,
                    val DGTZFS: String?,
                    val SYCH: String?,
                    val SFKZ: String?,
                    val XSLC: String?,
                    val Max_SD: String?,
                    val ZJDW: String?,
                    val BSQXS: String?,
                    val ZDFS: String?,
                    val WRZY: String?,
                    val QGS: String?,
                    val RyGG: String?,
                    val WZDW: String?,
                    val JGRQ: String?,
                    val Lsdh: String?,
                    val Lxdz: String?,
                    val Aj_Veh_Type: String?,
                    val Hj_Veh_Type: String?,
                    val Zj_Veh_Type: String?,
                    val hpys: String?,
                    val qdzhw: String?,
                    val qdzhsh: Int?,
                    val zhzhsh: Int?,
                    val zhchzhw: String?,
                    val edzhs: String?,
                    val ajywlb: String?,
                    val zjywlb: String?,
                    val hjywlb: String?,
                    val gb: String?,
                    val jdchsshlb: String?,
                    val jcxlb: String?,
                    val qzhsh: Int?,
                    val zhchzhsh: Int?,
                    val wgchx: String?,
                    val chych: String?,
                    val ednj: String?,
                    val ednjzhs: String?,
                    val glbzhfsh: String?,
                    val kchdj: String?,
                    val hchchshxsh: String?,
                    val qdzhzhl: String?,
                    val bzhzhw: String?,
                    val zhxzhsh: String?,
                    val zjjylb: String?,
                    val yyzhh: String?,
                    val Info_01: String?,
                    val Info_02: String?,
                    val Info_03: String?,
                    val Info_04: String?,
                    val Info_05: String?,
                    val Info_06: String?,
                    val Info_07: String?,
                    val Info_08: String?,
                    val Info_09: String?,
                    val Info_10: String?,
                    val zjlsh: String?,
                    val Bz01: String?,
                    val Bz02: String?,
                    val Bz03: String?,
                    val sfjf: String?,
                    val sfkp: String?,
                    val tb: String?,
                    val sfgp: String?,
                    val dzss: String?,
                    val kqxjzw: String?,
                    val zhchlsh: String?,
                    val hjxm: String?,
                    val sjr: String?,
                    val sjrsfzh: String?,
                    val sjrdh: String?
)
{
    constructor(
        id: String,
        Lsh: String,
        hpzl: String?,
        hphm: String?,
        ajywlb: String?,
        hjywlb: String?
    ) : this(
        id,
        Lsh,
        hpzl,
        hphm,
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
        ajywlb,
        null,
        hjywlb,
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
package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   机动车基本信息表2(BaseInfo_Net)
 *   @param id	自增序号	Int
 *   @param Lsh	机动车序号	varchar(17)
 *   @param hpzl	号牌种类	varchar(2)
 *   @param hphm	号牌号码	varchar(15)
 *   @param clpp1	中文品牌	varchar(32)
 *   @param clxh	车辆型号	varchar(32)
 *   @param clpp2	英文品牌	varchar(32)
 *   @param gcjk	国产/进口	varchar(1)
 *   @param zzg	制造国	varchar(3)
 *   @param zzcmc	制造厂名称	varchar(64)
 *   @param clsbdh	车辆识别代号	varchar(25)
 *   @param fdjh	发动机号	varchar(30)
 *   @param cllx	车辆类型	varchar(3)
 *   @param csys	车身颜色	varchar(5)
 *   @param syxz	使用性质	varchar(1)
 *   @param sfzmhm	身份证明号码	varchar(18)
 *   @param sfzmmc	身份证明名称	varchar(1)
 *   @param syr	机动车所有人	varchar(128)
 *   @param ccdjrq	初次登记日期	varchar(24)
 *   @param djrq	最近定检日期	varchar(24)
 *   @param yxqz	检验有效期止	varchar(24)
 *   @param qzbfqz	强制报废期止	varchar(24)
 *   @param fzjg	发证机关	varchar(10)
 *   @param glbm	管理部门	varchar(12)
 *   @param bxzzrq	保险终止日期	varchar(24)
 *   @param zt	机动车状态	varchar(6)
 *   @param dybj	抵押标记	varchar(1)
 *   @param fdjxh	发动机型号	varchar(64)
 *   @param rlzl	燃料种类	varchar(3)
 *   @param pl	排量	varchar(6)
 *   @param gl	功率	varchar(8)
 *   @param zxxs	转向形式	varchar(1)
 *   @param cwkc	车外廓长	varchar(5)
 *   @param cwkk	车外廓宽	varchar(4)
 *   @param cwkg	车外廓高	varchar(4)
 *   @param hxnbcd	货箱内部长度	varchar(5)
 *   @param hxnbkd	货箱内部宽度	varchar(4)
 *   @param hxnbgd	货箱内部高度	varchar(4)
 *   @param gbthps	钢板弹簧片数	varchar(3)
 *   @param zs	轴数	varchar(3)
 *   @param zj	轴距	varchar(5)
 *   @param qlj	前轮距	varchar(4)
 *   @param hlj	后轮距	varchar(4)
 *   @param ltgg	轮胎规格	varchar(64)
 *   @param lts	轮胎数	varchar(2)
 *   @param zzl	总质量	varchar(8)
 *   @param zbzl	整备质量	varchar(8)
 *   @param hdzzl	核定载质量	varchar(8)
 *   @param hdzk	核定载客	varchar(3)
 *   @param zqyzl	准牵引总质量	varchar(8)
 *   @param qpzk	驾驶室前排载客人数	varchar(1)
 *   @param hpzk	驾驶室后排载客人数	varchar(2)
 *   @param hbdbqk	环保达标情况	varchar(128)
 *   @param ccrq	出厂日期	varchar(24)
 *   @param clyt	车辆用途	varchar(2)
 *   @param ytsx	用途属性	varchar(1)
 *   @param xszbh	行驶证证芯编号	varchar(20)
 *   @param jyhgbzbh	检验合格标志	varchar(20)
 *   @param xzqh	管理辖区	varchar(10)
 *   @param zsxzqh	住所地址行政区划	varchar(128)
 *   @param zzxzqh	联系地址行政区划	varchar(128)
 *   @param sgcssbwqk	事故车损伤部位情况
 *   @param sfmj	是否免检
 *   @param bmjyy	不免检原因
 *   @param sfxny	是否新能源
 *   @param xnyzl	新能源汽车种类
 *   @param zjlsh	综检流水号
 *   @param Bz01	备注	varchar(128)
 *   @param Bz02	备注	varchar(128)
 *   @param Bz03	备注	varchar(128)
 *   @param sfazwb	是否安装尾板
 *   @param wbzl	尾板质量

 */
data class BaseInfo_Net(val id : String?,
                        val Lsh : String?,
                        val hpzl : String?,
                        val hphm : String?,
                        val clpp1 : String?,
                        val clxh : String?,
                        val clpp2 : String?,
                        val gcjk : String?,
                        val zzg : String?,
                        val zzcmc : String?,
                        val clsbdh : String?,
                        val fdjh : String?,
                        val cllx : String?,
                        val csys : String?,
                        val syxz : String?,
                        val sfzmhm : String?,
                        val sfzmmc : String?,
                        val syr : String?,
                        val ccdjrq : String?,
                        val djrq : String?,
                        val yxqz : String?,
                        val qzbfqz : String?,
                        val fzjg : String?,
                        val glbm : String?,
                        val bxzzrq : String?,
                        val zt : String?,
                        val dybj : String?,
                        val fdjxh : String?,
                        val rlzl : String?,
                        val pl : String?,
                        val gl : String?,
                        val zxxs : String?,
                        val cwkc : String?,
                        val cwkk : String?,
                        val cwkg : String?,
                        val hxnbcd : String?,
                        val hxnbkd : String?,
                        val hxnbgd : String?,
                        val gbthps : String?,
                        val zs : String?,
                        val zj : String?,
                        val qlj : String?,
                        val hlj : String?,
                        val ltgg : String?,
                        val lts : String?,
                        val zzl : String?,
                        val zbzl : String?,
                        val hdzzl : String?,
                        val hdzk : String?,
                        val zqyzl : String?,
                        val qpzk : String?,
                        val hpzk : String?,
                        val hbdbqk : String?,
                        val ccrq : String?,
                        val clyt : String?,
                        val ytsx : String?,
                        val xszbh : String?,
                        val jyhgbzbh : String?,
                        val xzqh : String?,
                        val zsxzqh : String?,
                        val zzxzqh : String?,
                        val sgcssbwqk : String?,
                        val sfmj : String?,
                        val bmjyy : String?,
                        val sfxny : String?,
                        val xnyzl : String?,
                        val zjlsh : String?,
                        val Bz01 : String?,
                        val Bz02 : String?,
                        val Bz03 : String?,
                        val sfazwb : String?,
                        val wbzl : String?

)
{
    constructor(

    ) : this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
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
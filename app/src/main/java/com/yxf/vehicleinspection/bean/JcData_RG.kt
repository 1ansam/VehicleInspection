package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   人工检验项目数据表(JcData_RG)
 *   @param id	自增编号	int
 *   @param lsh	流水号	varchar(32)
 *   @param hphm	号牌号码	varchar(20)
 *   @param hpzl	号牌种类	varchar(15)
 *   @param Jccs	检测次数	int
 *   @param jcdate	检测日期	varchar(16)
 *   @param kstime	开始时间	varchar(16)
 *   @param jstime	结束时间	varchar(16)
 *   @param jcpj	检测评价	varchar(2)
 *   @param dalb	检验项目类别	varchar(2)
 *   @param xmbh	项目编号	varchar(400)
 *   @param Bz1	不合格项目	text
 *   @param Bz2	整备质量	varchar(32)
 *   @param Bz3	外廓尺寸	varchar(32)
 *   @param Bz4	复检情况（停用）	varchar(MAX)
 *   @param Bz5	检验备注描述	varchar(200)
 *   @param Bz6	轮胎花纹深度	varchar(200)
 *   @param Bz7	轴距	varchar(200)
 *   @param Bz8	栏板高度	varchar(200)
 *   @param Bz9	对称部位高度差	varchar(200)
 *   @param Bz10	方向盘最大自由转动量

 */
data class JcData_RG(val id : Int?,
                     val lsh : String?,
                     val hphm : String?,
                     val hpzl : String?,
                     val Jccs : Int?,
                     val jcdate : String?,
                     val kstime : String?,
                     val jstime : String?,
                     val jcpj : String?,
                     val dalb : String?,
                     val xmbh : String?,
                     val Bz1 : String?,
                     val Bz2 : String?,
                     val Bz3 : String?,
                     val Bz4 : String?,
                     val Bz5 : String?,
                     val Bz6 : String?,
                     val Bz7 : String?,
                     val Bz8 : String?,
                     val Bz9 : String?,
                     val Bz10 : String?
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
        null
    )
}
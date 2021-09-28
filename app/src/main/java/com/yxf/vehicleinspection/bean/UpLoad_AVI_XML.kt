package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   录象记录存储文件(UpLoad_AVI_XML)
 *   @param SXH	上线号  检测线号	varchar(25)	NOT NULL
 *   @param jcbh	检测编号	varchar(25)	NOT NULL
 *   @param HPZL	号牌种类	varchar(24)
 *   @param hphm	号牌号码	varchar(24)
 *   @param JCXZ	检测性质	varchar(24)
 *   @param jcrq	检测日期	varchar(24)
 *   @param TimS	检测时间	varchar(16)
 *   @param jklx	作为检测次数	varchar(8)
 *   @param xmbh	项目编号	varchar(12)
 *   @param JcKsSj	检测开始时间	varchar(24)
 *   @param JcJsSj	检测结束时间	varchar(24)
 *   @param clpp	车架号	varchar(48)
 *   @param czdw	保存为录象文件名	varchar(125)
 *   @param upload_OK	上传标志	varchar(4)
 *   @param InBz_01		varchar(16)
 *   @param InBz_02		varchar(16)
 *   @param InBz_03		varchar(16)
 *   @param InBz_04		varchar(16)
 *   @param InBz_05	授权时间	varchar(16)
 *   @param InBz_06		varchar(16)

 */
data class UpLoad_AVI_XML(val SXH : String,
                          val jcbh : String,
                          val HPZL : String?,
                          val hphm : String?,
                          val JCXZ : String?,
                          val jcrq : String?,
                          val TimS : String?,
                          val jklx : String?,
                          val xmbh : String?,
                          val JcKsSj : String?,
                          val JcJsSj : String?,
                          val clpp : String?,
                          val czdw : String?,
                          val upload_OK : String?,
                          val InBz_01 : String?,
                          val InBz_02 : String?,
                          val InBz_03 : String?,
                          val InBz_04 : String?,
                          val InBz_05 : String?,
                          val InBz_06 : String?
){
    constructor(
        SXH: String,
        jcbh : String,
    ) : this(
        SXH,
        jcbh,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
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
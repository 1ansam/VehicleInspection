package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   检测人员签名表(QianMing_Info)
 *   @param id	自增编号	int	NOT NULL
 *   @param Lsh	流水号	varchar(32)	NOT NULL
 *   @param hphm	号牌号码	varchar(16)
 *   @param Jccs	检测次数	varchar(2)	NOT NULL
 *   @param JcXm	检测项目	varchar(16)
 *   @param Ry_Name	人员名称	varchar(32)
 *   @param JcDate	检测日期	varchar(32)
 *   @param Base64	图片	text
 *   @param Bz_01	备注	varchar(32)
 *   @param Bz_02	备注	varchar(32)
 *   @param Bz_03	备注	varchar(32)

 */
data class QianMing_Info(val id : String,
                         val Lsh : String,
                         val hphm : String?,
                         val Jccs : String,
                         val JcXm : String?,
                         val Ry_Name : String?,
                         val JcDate : String?,
                         val Base64 : String?,
                         val Bz_01 : String?,
                         val Bz_02 : String?,
                         val Bz_03 : String?,
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
        Jccs,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}
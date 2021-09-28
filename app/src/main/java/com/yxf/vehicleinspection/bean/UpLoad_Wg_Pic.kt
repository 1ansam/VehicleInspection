package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   照片存储列表(UpLoad_Wg_Pic)
 *   @param Jylsh	检验流水号	varchar(17)	NOT NULL
 *   @param Jyjgbh	检验机构编号	varchar(10)	NOT NULL
 *   @param Jcxdh	检测线代号	varchar(2)	NOT NULL
 *   @param Jycs	检验次数	int
 *   @param Hphm	号牌号码	varchar(15)
 *   @param Hpzl	号牌种类	varchar(2)
 *   @param Clsbdh	车辆识别代号	varchar(25)
 *   @param Zp	照片	text
 *   @param Pssj	拍摄时间	varchar(14)
 *   @param Jyxm	检验项目	varchar(2)
 *   @param Zpzl	照片种类	varchar(4)
 *   @param upload_OK	上传标志	varchar(8)
 *   @param BzO1	图片地址	varchar(8)
 *   @param BzO2	授权码	varchar(8)
 *   @param BzO3		varchar(8)
 *   @param ImageName	图片名	varchar(8)
 *   @param ImageIndex	图片位置id	int

 */
data class UpLoad_Wg_Pic(val Jylsh : String,
                         val Jyjgbh : String,
                         val Jcxdh : String,
                         val Jycs : Int?,
                         val Hphm : String?,
                         val Hpzl : String?,
                         val Clsbdh : String?,
                         val Zp : String?,
                         val Pssj : String?,
                         val Jyxm : String?,
                         val Zpzl : String?,
                         val upload_OK : String?,
                         val BzO1 : String?,
                         val BzO2 : String?,
                         val BzO3 : String?,
                         val ImageName : String?,
                         val ImageIndex : Int?,
){
    constructor(
        Jylsh: String,
        Jyjgbh : String,
        Jcxdh : String,
    ) : this(
        Jylsh,
        Jyjgbh,
        Jcxdh,
        null,
        null,
        null,
        null,
        null,
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
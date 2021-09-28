package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   照片种类(Pic_Sj_Bt_Pic_Type)
 *   @param Pic_Num	照片种类序号	varchar(8)		NOT NULL
 *   @param Pic_TypeStr	照片种类描述	varchar(64)
 *   @param Pic_DM	照片国标代码	varchar(8)
 *   @param Show_Bz	显示标志位	varchar(8)
 *   @param Pic_Bz01	备注	varchar(8)
 *   @param Pic_Bz02	备注	varchar(8)
 *   @param Pic_Bz03	备注	varchar(8)

 */
class Pic_Sj_Bt_Pic_Type(val Pic_Num : String,
                         val Pic_TypeStr : String?,
                         val Pic_DM : String?,
                         val Show_Bz : String?,
                         val Pic_Bz01 : String?,
                         val Pic_Bz02 : String?,
                         val Pic_Bz03 : String?,
)
{
    constructor(
        Pic_Num: String,
    ) : this(
        Pic_Num,
        null,
        null,
        null,
        null,
        null,
        null
    )
}
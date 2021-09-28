package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   人工检验项目信息表(QcyJcWgDpDmInfo)
 *   @param id	自增编号	int	NOT NULL
 *   @param Fl	分类	varchar(2)	NOT NULL
 *   @param FlMc	分类名称	varchar(16)	NOT NULL
 *   @param Dm	代码	varchar(4)	NOT NULL
 *   @param Mc	名称	varchar(128)	NOT NULL
 *   @param Bz	备注	varchar(8)

 */
data class QcyJcWgDpDmInfo(val id : Int,
                      val Fl : String,
                      val FlMc : String,
                      val Dm : String,
                      val Mc : String,
                      val Bz : String?,
)
{
    constructor(
        id: Int,
        Fl: String,
        FlMc: String,
        Dm : String,
        Mc: String
    ) : this(
        id,
        Fl,
        FlMc,
        Dm,
        Mc,
        null
    )
}
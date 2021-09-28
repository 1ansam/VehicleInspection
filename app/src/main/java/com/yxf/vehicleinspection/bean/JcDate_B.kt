package com.yxf.vehicleinspection.bean

import java.util.*

/**
 *   author:yxf
 *   time:2021/9/27
 *   行车制动检验数据表 (JcDate_B)
 *   @param id	自增编号	Int	NOT NULL
 *   @param Lsh	流水号	varchar(17)	NOT NULL
 *   @param hpzl	号牌种类	varchar(2)
 *   @param hphm	车牌号牌	varchar(15)
 *   @param Jccs	检测次数	int	NOT NULL
 *   @param JcDate	检测日期	date
 *   @param KsTime	开始时间	varchar(32)
 *   @param JsTime	结束时间	varchar(32)
 *   @param JcPj	检测结果	varchar(2)
 *   @param DaLB	数据类别	varchar(2)
 *   @param Data_lweight	左轮重	varchar(8)
 *   @param Data_rweight	右轮重	varchar(8)
 *   @param Data_lweight_dt	动态左轴重	varchar(8)
 *   @param Date_rweight_dt	动态右轴重	varchar(8)
 *   @param Data_lweight_jz	加载左轴重	varchar(8)
 *   @param Data_rweight_jz	加载右轴重	varchar(8)
 *   @param Data_lzd	左制动	varchar(8)
 *   @param Data_rzd	右制动	varchar(8)
 *   @param Data_lzdlc	左制动最大差值点	varchar(8)
 *   @param Data_rzdlc	右制动最大差值点	varchar(8)
 *   @param Data_zdh	制动率	varchar(8)
 *   @param Data_zdh_pj	制动率评价	varchar(8)
 *   @param Data_zdh_gb	制动率国标	varchar(32)
 *   @param Data_dtc	制动力不平衡率	varchar(8)
 *   @param Data_zdc_pj	不平衡率评价	varchar(8)
 *   @param Data_zdc_gb	不平衡率国标	varchar(32)
 *   @param Data_xtsj	协调时间	varchar(8)
 *   @param Data_xrsj_pj	协调时间评价	varchar(8)
 *   @param Data_xtsj_gb	协调时间国标	varchar(8)
 *   @param Data_tbl	未启用	varchar(8)
 *   @param Data_lzzl	左轮阻滞力	varchar(8)
 *   @param Data_lzzl_h	左轮阻滞率	varchar(8)
 *   @param Data_lzzl_pj	左轮阻滞率评价	varchar(8)
 *   @param Data_lzzl_gb	左轮组织率国标	varchar(8)
 *   @param Data_rzzl	右轮阻滞力	varchar(8)
 *   @param Data_rzzl_h	右轮阻滞率	varchar(8)
 *   @param Data_rzzl_pj	右轮阻滞率评价	varchar(8)
 *   @param Data_rzzl_gb	右轮阻滞率国标	varchar(8)
 *   @param Data_lzd_hand	左驻车制动力	varchar(8)
 *   @param Data_rzd_hand	右驻车制动力	varchar(8)
 *   @param Data_line	制动曲线	varchar(MAX)
 *   @param Data_zdpj	制动总评价	varchar(8)
 *   @param Data_zdh_pj_zj	综检制动和评价	varchar(8)
 *   @param Data_zdh_bz_zj	综检制动和评价标准	varchar(8)
 *   @param Data_zdc_pj_zj	综检制动差评价	varchar(8)
 *   @param Data_zdc_bz_zj	综检制动差评价标准	varchar(8)
 *   @param Data_zpj_zj	综检制动总评价	varchar(8)
 *   @param Bz1	动态轴重曲线	varchar(24)
 *   @param Bz2	备注	varchar(24)
 *   @param Bz3	备注	varchar(24)
 *   @param data_lzd_hand1	一轴左轮驻车制动力
 *   @param data_rzd_hand1	一轴右轮驻车制动力
 *   @param data_lzd_hand2	二轴左轮驻车制动力
 *   @param data_rzd_hand2	二轴右轮驻车制动力
 *   @param data_lzd_hand3	三轴左轮驻车制动力
 *   @param data_rzd_hand3	三轴右轮驻车制动力
 *   @param data_lzd_hand4	四轴左轮驻车制动力
 *   @param data_rzd_hand4	四轴右轮驻车制动力
 *   @param data_lzd_hand5	五轴左轮驻车制动力
 *   @param data_rzd_hand5	五轴右轮驻车制动力
 */
data class JcDate_B(val id : Int,
                    val Lsh : String,
                    val hpzl : String?,
                    val hphm : String?,
                    val Jccs : Int,
                    val JcDate : Date?,
                    val KsTime : String?,
                    val JsTime : String?,
                    val JcPj : String?,
                    val DaLB : String?,
                    val Data_lweight : String?,
                    val Data_rweight : String?,
                    val Data_lweight_dt : String?,
                    val Date_rweight_dt : String?,
                    val Data_lweight_jz : String?,
                    val Data_rweight_jz : String?,
                    val Data_lzd : String?,
                    val Data_rzd : String?,
                    val Data_lzdlc : String?,
                    val Data_rzdlc : String?,
                    val Data_zdh : String?,
                    val Data_zdh_pj : String?,
                    val Data_zdh_gb : String?,
                    val Data_dtc : String?,
                    val Data_zdc_pj : String?,
                    val Data_zdc_gb : String?,
                    val Data_xtsj : String?,
                    val Data_xrsj_pj : String?,
                    val Data_xtsj_gb : String?,
                    val Data_tbl : String?,
                    val Data_lzzl : String?,
                    val Data_lzzl_h : String?,
                    val Data_lzzl_pj : String?,
                    val Data_lzzl_gb : String?,
                    val Data_rzzl : String?,
                    val Data_rzzl_h : String?,
                    val Data_rzzl_pj : String?,
                    val Data_rzzl_gb : String?,
                    val Data_lzd_hand : String?,
                    val Data_rzd_hand : String?,
                    val Data_line : String?,
                    val Data_zdpj : String?,
                    val Data_zdh_pj_zj : String?,
                    val Data_zdh_bz_zj : String?,
                    val Data_zdc_pj_zj : String?,
                    val Data_zdc_bz_zj : String?,
                    val Data_zpj_zj : String?,
                    val Bz1 : String?,
                    val Bz2 : String?,
                    val Bz3 : String?,
                    val data_lzd_hand1 : String?,
                    val data_rzd_hand1 : String?,
                    val data_lzd_hand2 : String?,
                    val data_rzd_hand2 : String?,
                    val data_lzd_hand3 : String?,
                    val data_rzd_hand3 : String?,
                    val data_lzd_hand4 : String?,
                    val data_rzd_hand4 : String?,
                    val data_lzd_hand5 : String?,
                    val data_rzd_hand5 : String?
)
{
    constructor(
        id: Int,
        Lsh: String,
        Jccs: Int
    ) : this(
        id,
        Lsh,
        null,
        null,
        Jccs,
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
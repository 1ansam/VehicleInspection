package com.yxf.vehicleinspection.bean

import java.io.Serializable

/**
 *   author:yxf
 *   time:2021/10/11
 *   @param Lsh 流水号
 *   @param Hphm 号牌号码
 *   @param Hpzl 号牌种类
 *   @param HpzlCc 号牌种类汉字标识
 *   @param Djrq 登记日期
 *   @param Ywlb 业务类别
 *   @param Jyzt 检验状态
 *   @param Hpys 号牌颜色
 *   @param HpysCc 号牌颜色汉字
 *   @param Ajywlb 安检业务类别
 *   @param AjywlbCc 安检业务类别汉字
 *   @param Hjywlb 环检业务类别
 *   @param HjywlbCc 环检业务类别汉字

 */
data class VehicleQueue (
    val Lsh : String?,
    val Hphm : String?,
    val Hpzl : String?,
    val HpzlCc : String?,
    val Djrq : String?,
    val Ywlb : String?,
    val Jyzt : String?,
    val Hpys : String?,
    val HpysCc : String?,
    val Ajywlb : String?,
    val AjywlbCc : String?,
    val Hjywlb : String?,
    val HjywlbCc : String?,
        ): Serializable

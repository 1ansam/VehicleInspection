package com.yxf.vehicleinspection.bean.request

/**
 *   author:yxf
 *   time:2021/11/4
 *   调用接口，查询机动车详细信息。查询条件“Lsh,Hpzl,Hphm,Clsbdh,Xszbh”分为3中查询组合，分别为:
 *   1、“Lsh”:按流水号查询。
 *   2、“Hpzl”,”Hphm”，“Clsbdh”:按照号牌种类，号牌号码，车辆识别代号（后4位）查询。
 *   3、“Xszbh”:按照行驶证编号查询。
 *   三种查询条件必须满足其中一种，不可同时为空，可以同时满足多个条件。
 *   @param Lsh 流水号 VARCHAR(20) 按流水号查询时其他参数可空（空字符串）
 *   @param Hphm 号牌号码 用流水号/行驶证编号查询时可空（空字符串）
 *   @param Hpzl 号牌种类 用流水号/行驶证编号查询时可空（空字符串）
 *   @param Clsbdh 车辆识别代号 用流水号/行驶证编号查询时可空（空字符串）
 *   @param Xszbh 行驶证编号 按行驶证编号查询时其他参数可空（空字符串）
 */
data class VehicleAllInfoR005Request(
    val Lsh: String,
    val Hphm: String,
    val Hpzl: String,
    val Clsbdh: String,
    val Xszbh: String
)
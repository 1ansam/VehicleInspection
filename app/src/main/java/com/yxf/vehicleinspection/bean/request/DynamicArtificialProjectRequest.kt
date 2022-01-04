package com.yxf.vehicleinspection.bean.request

/**
 *   author:yxf
 *   time:2021/11/12
 *   底盘动态人工检验项目
 *   @param Jyjgbh 检验机构编号安检
 *   @param Jcxh 检测线号
 *   @param Hphm 号牌号码
 *   @param Hpzl 号牌种类
 *   @param Clsbdh 车辆识别代号
 *   @param Jyxm 检验项目
 *   @param Ajywlb 安检业务类别
 *   @param Hjywlb 环检业务类别
 *   @param AjJkxlh 安检接口序列号
 *   @param Xmlb 项目列表
 *   @param Jckssj 检测开始时间 yyyyMMddHHmmss
 *   @param Jcjssj 检测开始时间 yyyyMMddHHmmss
 *   @param Fxpzdzyzdl 方向盘最大自由转动量
 *   @param Jyyjy 检验员建议
 *   @param Dpdtjyy 底盘动态检验员
 *   @param Dpdtjyysfzh 底盘动态检验员身份证号
 *   @param Ycy 引车员
 *   @param Ycysfzh 引车员身份证号
 *   @param Ycyjy 引车员建议
 *   @param Bz 备注
 *   @param Ajlsh 安检流水号
 *   @param Hjlsh 环检流水号
 *   @param Ajjccs 安检检测次数
 *   @param Hjjccs 环检检测次数
 */
data class DynamicArtificialProjectRequest(
    val Jyjgbh : String,
    val Jcxh : String,
    val Hphm : String,
    val Hpzl : String,
    val Clsbdh : String,
    val Jyxm : String,
    val Ajywlb : String,
    val Hjywlb : String,
    val AjJkxlh : String,
    val Xmlb : List<Xmlb>,
    val Jckssj : String,
    val Jcjssj : String,
    val Fxpzdzyzdl : String,
    val Jyyjy : String,
    val Dpdtjyy : String,
    val Dpdtjyysfzh : String,
    val Ycy : String,
    val Ycysfzh : String,
    val Ycyjy : String,
    val Bz : String,
    val Ajlsh : String,
    val Hjlsh : String,
    val Ajjccs : Int,
    val Hjjccs : Int,

)
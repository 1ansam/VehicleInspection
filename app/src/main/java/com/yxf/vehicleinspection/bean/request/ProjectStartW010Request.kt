package com.yxf.vehicleinspection.bean.request

/**
 *   author:yxf
 *   time:2021/11/18
 *   @param Lsh 流水号
 *   @param Jyjgbh 检验机构编号安检
 *   @param Jcxh 检测线代号
 *   @param Jccs 检测次数
 *   @param Hphm 号牌号码
 *   @param Hpzl 号牌种类
 *   @param Clsbdh 车辆识别代号
 *   @param Jyxm 检验项目
 *   @param Gwjysbbh 工位检验设备编号
 *   @param Kssj 开始时间 yyyy-MM-dd HH:mm:ss
 *   @param Ajywlb 安检业务类别
 *   @param Hjywlb 环检业务类别
 *   @param AjJkxlh 安检接口序列号
 */
data class ProjectStartW010Request(
    val Jyjgbh : String,
    val Jcxh : String,
    val Hphm : String,
    val Hpzl : String,
    val Clsbdh : String,
    val Jyxm : String,
    val Gwjysbbh : String,
    val Kssj : String,
    val Ajywlb : String,
    val Hjywlb : String,
    val AjJkxlh : String,
    val Ajlsh : String,
    val Hjlsh : String,
    val Ajjccs : Int,
    val Hjjccs : Int,


) {
}
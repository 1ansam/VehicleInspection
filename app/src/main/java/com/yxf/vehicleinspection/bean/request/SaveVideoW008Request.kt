package com.yxf.vehicleinspection.bean.request

/**
 *   author:yxf
 *   time:2021/11/12
 *   @param ID 编号
 *   @param Lsh 流水号
 *   @param Jcxh 检测线代号
 *   @param Jccs 检测次数
 *   @param Hphm 号牌号码
 *   @param Hpzl 号牌种类
 *   @param Jcxm 检验项目
 *   @param Spbhaj 视频编号 安检
 *   @param Spbhhj 视频编号 环检
 *   @param Ajywlb 安检业务类别
 *   @param Hjywlb 环检业务类别
 *   @param Jcrq 检测日期
 *   @param Jcsj 检测时间
 *   @param Jckssj 检测开始时间
 *   @param Jcjssj 检测结束时间
 *   @param Lxxx 录像信息
 *   @param Clpp 车辆品牌
 *   @param Czdw 车主单位
 *   @param Bcaj 保存安检
 *   @param BcHj 保存环检
 *   @param Hjdlsj 环检登陆时间
 *   @param Lxdz 录像地址 （接口返回）
 *   @param Lxbz 录像标志 （0=无上传后台截取 1=已上传）
 */
data class SaveVideoW008Request(
    val ID : Int,
    val Lsh : String,
    val Jcxh : String,
    val Jccs : Int,
    val Hphm : String,
    val Hpzl : String,
    val Jcxm : String,
    val Spbhaj : String,
    val Spbhhj : String,
    val Ajywlb : String,
    val Hjywlb : String,
    val Jcrq : String,
    val Jcsj : String,
    val Jckssj : String,
    val Jcjssj : String,
    val Lxxx : String,
    val Clpp : String,
    val Czdw : String,
    val Bcaj : String,
    val BcHj : String,
    val Hjdlsj : String,
    val Lxdz : String,
    val Lxbz : String,
) {
}
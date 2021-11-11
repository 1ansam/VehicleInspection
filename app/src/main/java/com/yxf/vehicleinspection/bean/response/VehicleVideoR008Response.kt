package com.yxf.vehicleinspection.bean.response

/**
 * @author : yxf
 * @Date : 2021/10/11
 * @param ID 编号
 * @param Lsh 流水号
 * @param SXH 检测线代号
 * @param Jccs 检验次数
 * @param Hphm 号牌号码
 * @param Hpzl 号牌种类
 * @param Jcsj 检测时间
 * @param Xmbh 视频项目编号
 * @param JcKsSj 检测开始时间
 * @param JcJsSj 检测结束时间
 * @param Lxdz 录像地址
 * @param Spmc 视频名称

 **/
data class VehicleVideoR008Response(
    val ID : String?,
    val Lsh  : String?,
    val SXH : String?,
    val Jccs : String?,
    val Hphm : String?,
    val Hpzl : String?,
    val Jcsj : String?,
    val Xmbh : String?,
    val JcKsSj : String?,
    val JcJsSj : String?,
    val Lxdz : String?,
    val Spmc : String?,

) {
}
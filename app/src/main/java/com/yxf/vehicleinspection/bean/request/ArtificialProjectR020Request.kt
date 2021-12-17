package com.yxf.vehicleinspection.bean.request

/**
 *   author:yxf
 *   time:2021/11/9
 *   LYYDJKR020请求bean
 *   查询车辆人工检验项目信息
 *   @param AjLsh 安检流水号
 *   @param HjLsh 环检流水号
 *   @param Jyxm 检验项目 F1、C1、DC、UC、NQ
 *   @param Ajywlb 安检业务类别 不检测时填“-”
 *   @param Hjywlb 环检业务类别 不检测时填“-”
 */
data class ArtificialProjectR020Request(
    val Jyxm : String,
    val Ajywlb : String,
    val Hjywlb : String,
    val Ajlsh : String,
    val Hjlsh : String,


) {
}
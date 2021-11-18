package com.yxf.vehicleinspection.bean.response

/**
 *   author:yxf
 *   time:2021/11/18
 *   @param ID 编号 int 1
 *   @param Jkbh 接口编号
 *   @param Spbh 视频编号
 *   @param BcjgAj 保存结果 安检
 *   @param BcsbyyAj 保存失败原因  安检
 *   @param BcjgHj 保存结果  环检
 *   @param BcsbyyHj 保存失败原因  环检
 */
class ProjectStartW010Response(
    val ID : String,
    val Jkbh : String,
    val Spbh : String,
    val BcjgAj : String,
    val BcsbyyAj : String,
    val BcjgHj : String,
    val BcsbyyHj : String,

) {
}
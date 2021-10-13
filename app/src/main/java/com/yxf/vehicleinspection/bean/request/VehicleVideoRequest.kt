package com.yxf.vehicleinspection.bean.request

/**
 * @author yxf
 * 调用接口，根据流水号（Lsh）和检测次数（Jccs）获取检验视频信息，
 * 其中流水号（Lsh）不能为空，当检测次数（Jccs）为空或者＝0时，返回流水号所有检验视频。
 * @param Lsh 流水号
 * @param Jccs 检测次数
 */
data class VehicleVideoRequest(
    val Lsh : String,
    val Jccs : String?
)
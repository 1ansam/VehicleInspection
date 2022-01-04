package com.yxf.vehicleinspection.bean.request

/**
 * @author yxf
 * 调用接口，根据流水号（Lsh）和检测次数（Jccs）获取检验视频信息，
 * @param Lsh 流水号
 * @param Jccs 检测次数
 */
data class VehicleVideoR008Request(
    val Ajlsh : String,
    val Hjlsh : String,
)
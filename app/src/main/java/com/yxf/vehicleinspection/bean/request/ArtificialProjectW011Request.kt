package com.yxf.vehicleinspection.bean.request

/**
 *   author:yxf
 *   time:2021/11/12
 *   LYYDJKW011请求bean
 *   上传人工检验结果数据
 *   @param Jyxm 检验项目
 *   @param Jcsj 检测数据
 *   @param T 检测数据bean 见See Also
 *   @see ExteriorArtificialProjectRequest
 *   @see ChassisArtificialProjectRequest
 *   @see DynamicArtificialProjectRequest
 *   @see NetworkQueryArtificialProjectRequest
 *   @see UniqueArtificialProjectRequest
 */
data class ArtificialProjectW011Request<T>(
    val Jyxm : String,
    val Jcsj : T
)

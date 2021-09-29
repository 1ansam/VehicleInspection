package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/29
 */
data class InspectionInfoBean(
    val title : String,
    val itemBeanList:ArrayList<InspectionInfoItemBean>
)

data class InspectionInfoItemBean(
    val itemName : String,
    val itemValue: String
)
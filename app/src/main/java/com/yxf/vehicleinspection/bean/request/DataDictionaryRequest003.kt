package com.yxf.vehicleinspection.bean.request

/**
 * @author : yxf
 * @Date : 2021/10/11
 * 调用接口，查询数据字典中各分类代码代表的含义，
 * 参数 分类（Fl）为必填参数，代码（dm）,名称（mc）为选填参数，
 * 当代码（dm）,名称（mc）都为空时，返回分类（fl）所表示的多有内容，
 * 当代码（dm）,名称（mc）不为空时，返回分类（fl）中对应的条目。
 * 数据字典以安检数据库为准！
 * @param Fl 分类
 * @param Dm 代码
 * @param Mc 名称
 **/
data class DataDictionaryRequest003(
    val Fl : String?,
    val Dm : String?,
    val Mc : String?

){
    constructor() : this(null,null,null)
}
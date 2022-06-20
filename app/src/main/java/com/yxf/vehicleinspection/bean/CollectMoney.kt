package com.yxf.vehicleinspection.bean

import java.io.Serializable

/**
 *   author:yxf
 *   time:2021/12/21
 *   收费信息集合
 */
data class CollectMoney(
    // SystemParams数据库中的appid
    val appid : String,
    // SystemParams数据库中的C
    val c : String,
    // yyyyMMddHHmmss+3位随机数字
    val oid : String,
    // 总金额 单位为分
    val amt : Int,
    //附加信息 格式为：
    //05|Q1#号牌号码|X#其他备注信息
    val trxreserve : String,
    //签名校验
    //此类中除sign之外的所有数据完整后按unicode排序 调用String.compareTo方法
    //进行拼接 格式为：
    //key1=value1&key2=value2......
    //之后进行MD5加密 得到sign
    val sign : String,
    //SystemParams数据库中的MD5Key
    val key : String,
) : Serializable {

}
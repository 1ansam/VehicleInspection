package com.yxf.vehicleinspection.bean.response

/**
 * @author yxf
 * @param ID 编号
 * @param Lsh 流水号
 * @param Jcxdh 检测线代号
 * @param Jycs 检验次数
 * @param Hphm 号牌号码
 * @param Hpzl 号牌种类
 * @param Zp 照片 base64
 * @param Pssj 拍摄时间  yyyyMMddHHmmss
 * @param Jyxm 检验项目
 * @param Zpzl 照片种类
 * @param Ywlb 业务类别 1=上传  2=不上传
 * @param ImageName 照片地址
 * @param ZpzlMc 照片种类名称

 */
data class VehicleImageResponse(
    val ID : String?,
    val Lsh : String?,
    val Jcxdh : String?,
    val Jycs : String?,
    val Hphm : String?,
    val Hpzl : String?,
    val Zp : String?,
    val Pssj : String?,
    val Jyxm : String?,
    val Zpzl : String?,
    val Ywlb : String?,
    val ImageName : String?,
    val ZpzlMc : String?

)
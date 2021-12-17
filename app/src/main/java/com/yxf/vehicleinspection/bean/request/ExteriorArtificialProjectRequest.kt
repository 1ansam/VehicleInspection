package com.yxf.vehicleinspection.bean.request

/**
 *   author:yxf
 *   time:2021/11/12
 *   外观检验数据bean
 *   @param Lsh 流水号
 *   @param Jyjgbh 检验机构编号安检
 *   @param Jcxh 检测线代号
 *   @param Jccs 检测次数
 *   @param Hphm 号牌号码
 *   @param Hpzl 号牌种类
 *   @param Clsbdh 车辆识别代号
 *   @param Jyxm 检验项目 填写“F1”
 *   @param Ajywlb 安检业务类别
 *   @param Hjywlb 环检业务类别
 *   @param AjJkxlh 安检接口序列号
 *   @param Xmlb 项目列表
 *   @param Cwkc 车外廓长
 *   @param Cwkk  车外廓宽
 *   @param Cwkg 车外廓高
 *   @param Zj 轴距
 *   @param Cxlbgd 车厢栏板高度
 *   @param Dczxlhwsd 单车转向轮轮胎花纹深度
 *   @param Dcqtlhwsd 单车其他轮轮胎花纹深度
 *   @param Gchwsd 挂车轮胎花纹深度
 *   @param Yzzgd 第一轴左高度
 *   @param Yzygd 第一轴右高度
 *   @param Yzzygdc 第一轴左右高度差
 *   @param Zhzzgd 最后轴左高度
 *   @param Zhzygd 最后轴右高度
 *   @param Zhzzygdc 最后轴左右高度差
 *   @param Sfqssq 是否全时/适时四驱
 *   @param Sfdzzc 驻车制动是否使用电子控制装置
 *   @param Sfkqxj 是否配备空气悬架
 *   @param Kqxjz 空气悬架轴
 *   @param Zxzsl 转向轴数量
 *   @param Jyyjy 检验员建议
 *   @param Wgjcjyy 外观检验员
 *   @param Wgjcjyysfzh 外观检验员身份证号
 *   @param Bz 备注
 */
data class ExteriorArtificialProjectRequest(
    val Jyjgbh : String,
    val Jcxh : String,
    val Hphm : String,
    val Hpzl : String,
    val Clsbdh : String,
    val Jyxm : String ,
    val Ajywlb : String,
    val Hjywlb : String,
    val AjJkxlh : String,
    val XmlbAJ : List<Xmlb>,
    val XmlbHJ : List<Xmlb>,
    val Jckssj : String,
    val Jcjssj : String,
    val Cwkc : String,
    val Cwkk : String,
    val Cwkg : String,
    val Zj : String,
    val Cxlbgd : String,
    val Dczxlhwsd : String,
    val Dcqtlhwsd : String,
    val Gchwsd : String,
    val Yzzgd : String,
    val Yzygd : String,
    val Yzzygdc : String,
    val Zhzzgd : String,
    val Zhzygd : String,
    val Zhzzygdc : String,
    val Sfqssq : String,
    val Sfdzzc : String,
    val Sfkqxj : String,
    val Kqxjz : String,
    val Zxzsl : String,
    val Jyyjy : String,
    val Wgjcjyy : String,
    val Wgjcjyysfzh : String,
    val Bz : String,
    val Ajlsh : String,
    val Hjlsh : String,
    val Ajjccs : Int,
    val Hjjccs : Int,

)
package com.yxf.vehicleinspection.repository

/**
 *   author:yxf
 *   time:2021/12/2
 */
class RegisterRepository() {

    fun getTextMap() : Map<String, String> {
        val textMap = mutableMapOf<String,String>()
        textMap["Xszbh"] = "行驶证编号"
        textMap["Clpp1"] = "车辆品牌1"
        textMap["Clxh"] = "车辆型号"
        textMap["Fdjxh"] = "发动机型号"
        textMap["Fdjh"] = "发动机号"
        textMap["Syr"] = "所有人"
        textMap["Lxdh"] = "联系电话"
        textMap["Zt"] = "机动车状态"
        textMap["Zzcmc"] = "制造厂名称"
        textMap["Qdzs"] = "驱动轴数"
        textMap["Qdzw"] = "驱动轴位"
        textMap["Zczs"] = "驻车轴数"
        textMap["Zs"] = "轴数"
        textMap["Zj"] = "轴距"
        textMap["Zzs"] = "主轴数"
        textMap["Qzs"] = "前轴数"
        textMap["Qlj"] = "前轮距"
        textMap["Hlj"] = "后轮距"
        textMap["Zzl"] = "总质量"
        textMap["Zbzl"] = "整备质量"
        textMap["Kqxjzw"] = "空气悬架轴位"
        textMap["Zxzs"] = "转向轴数"
        textMap["Bzzw"] = "并装轴位"
        textMap["Hdzk"] = "核定载客"
        textMap["Hdzzl"] = "核定载质量"
        textMap["Zqyzl"] = "准牵引质量"
        textMap["Zdsjcs"] = "最大设计车速"
        textMap["Gl"] = "功率"
        textMap["Pl"] = "排量"
        textMap["Pqgs"] = "排气管数"
        textMap["Lcbds"] = "里程表读数"
        textMap["Edzs"] = "额定转速"
        textMap["Ltgg"] = "轮胎规格"
        textMap["Qgs"] = "气缸数"
        textMap["Lxdz"] = "联系地址"
        textMap["Sjr"] = "送检人姓名"
        textMap["Sjrdh"] = "送检人电话"
        textMap["Sjrsfzh"] = "送检人身份证号"
        textMap["SCR"] = "SCR（柴油）"
        textMap["DPF"] = "DPF（柴油）"
        return textMap
    }


    fun getSpinnerMap(): Map<String,String>{
        val spinnerMap = mutableMapOf<String,String>()
        spinnerMap ["Cllx"] = "车辆类型"
        spinnerMap ["Clyt"] = "车辆用途"
        spinnerMap ["Ytsx"] = "用途属性"
        spinnerMap ["Wgchx"] = "外观车型"
        spinnerMap ["Gcjk"] = "国产/进口"
        spinnerMap ["Qdxs"] = "驱动形式"
        spinnerMap ["Zdly"] = "制动力源"
//        spinnerMap ["Xzqh"] = "行政区划"
        spinnerMap ["Rlzl1"] = "燃料种类1"
        spinnerMap ["Rlzl2"] = "燃料种类2"
        spinnerMap ["Rygg"] = "燃油规格"
        spinnerMap ["Qzdz"] = "前照灯制"
        spinnerMap ["Jcxh"] = "检测线号"
        spinnerMap ["Jdcsslb"] = "机动车所属类别"
        spinnerMap ["Gyfs"] = "供油方式"
        spinnerMap ["Jqfs"] = "进气方式"
        spinnerMap ["Bsxs"] = "变速型式"
        spinnerMap ["Dws"] = "档位数"
        spinnerMap ["Ccs"] = "冲程数"
        spinnerMap ["Hclfs"] = "后处理方式"
        return spinnerMap
    }

}
package com.yxf.vehicleinspection.bean.request

/**
 * 调用接口查询机动车检验队列，条件（hphm）为空时，查询登陆时间为当天的车辆，
 * 条件（hphm）不为空时，模糊查询所有满足条件的车辆信息。
 * @param hphm 号牌号码
 */
data class VehicleQueueRequest(val hphm : String?)
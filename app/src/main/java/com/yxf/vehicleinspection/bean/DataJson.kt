package com.yxf.vehicleinspection.bean

import java.io.Serializable

/**
 * example:
 *
 * {
 * "code" : "1",
 * "data": [
 *              {
 *                   "hphm": "晋K02661",
 *                   "hpzl": "02",
 *                   "ajywlb": "01",
 *                   "hjywlb": "3",
 *                   "lsh": "D20211001193001",
 *                   "time": "2021-10-01 19:30:01"
 *              }
 *
 *              ...
 *
 *              ],
 * "message": "success"
 * }
 */
data class DataJson(
    val code: String,
    val data: ArrayList<Data>,
    val message: String
)

data class Data(
    val ajywlb: String,
    val hjywlb: String,
    val hphm: String,
    val hpzl: String,
    val lsh: String,
    val time: String
) : Serializable
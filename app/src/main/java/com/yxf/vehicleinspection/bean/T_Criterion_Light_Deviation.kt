package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   前照灯偏差标准信息表(T_Criterion_Light_Deviation)
 *   @param id	自增编号	int	NOT NULL
 *   @param jdclx	机动车类型	nvarchar(50)	NOT NULL
 *   @param ygczpymax	远光垂直偏移上限	nvarchar(50)
 *   @param ygczpymin	远光垂直偏移下限	nvarchar(50)
 *   @param ygsppyLeft_L	远光水平左灯左偏移	nvarchar(50)
 *   @param ygsppyLeft_R	远光水平左灯右偏移	nvarchar(50)
 *   @param ygsppyRight_L	远光水平右灯左偏移	nvarchar(50)
 *   @param ygsppyRight_R	远光水平右灯右偏移	nvarchar(50)
 *   @param jgczpymax_up	近光垂直偏移上限（灯高大于1000时）	nvarchar(50)
 *   @param jgczpymin_up	近光垂直偏移下限(灯高大于1000时)	nvarchar(50)
 *   @param jgczpymax_under	近光垂直偏移上限(灯高小于1000时)	nvarchar(50)
 *   @param jgczpymin_under	近光垂直偏移下限(灯高小于1000时)	nvarchar(50)
 *   @param jgsppyLeft_L	近光水平左灯左偏移	nvarchar(50)
 *   @param jgsppyRight_L	近光水平右灯左偏移	nvarchar(50)
 *   @param jgsppyLeft_R	近光水平左灯右偏移	nvarchar(50)
 *   @param jgsppyRight_R	近光水平右灯右偏移	nvarchar(50)

 */
data class T_Criterion_Light_Deviation(val id : Int,
                                       val jdclx : String,
                                       val ygczpymax : String?,
                                       val ygczpymin : String?,
                                       val ygsppyLeft_L : String?,
                                       val ygsppyLeft_R : String?,
                                       val ygsppyRight_L : String?,
                                       val ygsppyRight_R : String?,
                                       val jgczpymax_up : String?,
                                       val jgczpymin_up : String?,
                                       val jgczpymax_under : String?,
                                       val jgczpymin_under : String?,
                                       val jgsppyLeft_L : String?,
                                       val jgsppyRight_L : String?,
                                       val jgsppyLeft_R : String?,
                                       val jgsppyRight_R : String?
){
    constructor(
        id: Int,
        Lsh: String,
    ) : this(
        id,
        Lsh,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}
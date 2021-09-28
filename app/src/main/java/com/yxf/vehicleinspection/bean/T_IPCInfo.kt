package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/9/27
 *   检测摄象头参数(T_IPCInfo)
 *   @param ID	数据编号，自增量	int	NOT NULL
 *   @param LineNumber	线号	nvarchar](16)	NOT NULL
 *   @param WorkplaceCode	工位编号	nvarchar(16)	NOT NULL
 *   @param IPCIP	摄像头地址	nvarchar](64)
 *   @param IPCPort	摄像头端口号(默认8000)	nvarchar(16)
 *   @param IPCAccount	摄像头登录名	nvarchar(32)
 *   @param IPCPassword	摄像头密码	nvarchar(64)
 *   @param NVRIP	对应录像机地址	nvarchar(64)
 *   @param NVRPort	对应录像机端口号(默认8000)	nvarchar(16)
 *   @param NVRAccount	对应录像机登录名	nvarchar(32)
 *   @param NVRPassword	对应录像机密码	nvarchar(64)
 *   @param NVRChannel	对应录像机内该摄像头的通道号	nvarchar(16)
 *   @param IsAutomatic	是否自动工位	char(1)
 *   @param Bz1	扩展1	nvarchar(128)
 *   @param Bz2	扩展2	nvarchar(128)
 *   @param Bz3	扩展3	nvarchar(128)

 */
data class T_IPCInfo(
    val ID: String,
    val LineNumber: String,
    val WorkplaceCode: String,
    val IPCIP: String?,
    val IPCPort: String?,
    val IPCAccount: String?,
    val IPCPassword: String?,
    val NVRIP: String?,
    val NVRPort: String?,
    val NVRAccount: String?,
    val NVRPassword: String?,
    val NVRChannel: String?,
    val IsAutomatic: String?,
    val Bz1: String?,
    val Bz2: String?,
    val Bz3: String?,
)
{
    constructor(
        ID: String,
        LineNumber: String,
        WorkplaceCode: String
    ) : this(
        ID,
        LineNumber,
        WorkplaceCode,
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
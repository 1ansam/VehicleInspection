package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.*
import com.yxf.vehicleinspection.bean.response.*
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.service.UploadFile
import com.yxf.vehicleinspection.service.WriteService
import com.yxf.vehicleinspection.singleton.GsonSingleton
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import kotlin.reflect.typeOf

/**
 *   author:yxf
 *   time:2021/11/4
 */
class InspectionItemRepository {
    /**
     * 从服务器获取所有用户信息
     */
    fun getUserInfo(): LiveData<List<UserInfoR001Response>> {
        val liveData = MutableLiveData<List<UserInfoR001Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_ALL_USER,
            getIpAddress(),
            getJsonData(AllUserInfoR001Request())
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2ListBean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    MyApp.context,
                    t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        return liveData
    }
    /**
     * 从服务器获取检验照片信息
     * @param Jyxm 检验项目
     * @param Ajywlb 安检业务类别
     * @param Hjywlb 环检业务类别
     * @param Ajlsh 安检流水号
     * @param Hjlsh 环检流水号
     */
    fun getImageItemData(
        Jyxm: String,
        Ajywlb: String,
        Hjywlb: String,
        Ajlsh: String,
        Hjlsh: String,
    ): LiveData<List<ImageItemR017Response>> {
        val liveData = MutableLiveData<List<ImageItemR017Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_IMAGE_ITEM,
            getIpAddress(),
            getJsonData(ImageItemR017Request(Jyxm, Ajywlb, Hjywlb, Ajlsh, Hjlsh))
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2ListBean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
    /**
     * 从服务器获取人工检验项目
     * @param Jyxm 检验项目
     * @param Ajywlb 安检业务类别
     * @param Hjywlb 环检业务类别
     * @param Ajlsh 安检流水号
     * @param Hjlsh 环检流水号
     */
    fun getSelectItemData(
        Jyxm: String,
        Ajywlb: String,
        Hjywlb: String,
        Ajlsh: String,
        Hjlsh: String,
    ): LiveData<List<ArtificialProjectR020Response>> {
        val liveData = MutableLiveData<List<ArtificialProjectR020Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_ARTIFICIAL_PROJECT,
            getIpAddress(),
            getJsonData(ArtificialProjectR020Request(Jyxm, Ajywlb, Hjywlb, Ajlsh, Hjlsh))
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2ListBean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }

    /**
     * 联网查询内需要填写项目列表
     */
    fun getNetworkQueryInfoName(): List<String> {
        return listOf("联网查询结果描述", "机动车所有人", "手机号码", "联系地址", "邮政编码")
    }

    /**
     * 上传机动车检验照片信息
     */
    fun postInspectionPhotoW007(list: List<InspectionPhotoW007Request>): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val call =
            RetrofitService.create(WriteService::class.java).write(
                WRITE_INSPECTION_PHOTO,
                getIpAddress(),
                getJsonData(list)
            )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Boolean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                liveData.value = false
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
    /**
     * 上传机动车检验照片信息
     */
    fun postInspectionPhotoW007(inspectionPhotoW007Request: InspectionPhotoW007Request): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val call =
            RetrofitService.create(WriteService::class.java).write(
                WRITE_INSPECTION_PHOTO,
                getIpAddress(),
                getJsonData(inspectionPhotoW007Request)
            )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Boolean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                liveData.value = false
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
    /**
     * 上传人工检验项目信息
     *   @param T 检测数据bean 见See Also
     *   @see ExteriorArtificialProjectRequest
     *   @see ChassisArtificialProjectRequest
     *   @see DynamicArtificialProjectRequest
     *   @see NetworkQueryArtificialProjectRequest
     *   @see UniqueArtificialProjectRequest
     */
    fun <T> postArtificialProjectW011(list: List<ArtificialProjectW011Request<T>>): MutableLiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_ARTIFICIAL_PROJECT,
            getIpAddress(),
            getJsonData(list)
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Boolean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                liveData.value = false
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
    /**
     * 上传保存视频信息
     */
    fun postSaveVideoW008(saveVideoW008Request: SaveVideoW008Request): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_SAVE_VIDEO,
            getIpAddress(),
            getJsonData(saveVideoW008Request)
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Boolean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                liveData.value = false
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
    /**
     * 上传项目开始信息
     */
    fun postProjectStartW010(projectStartW010Request: ProjectStartW010Request): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_PROJECT_START,
            getIpAddress(),
            getJsonData(projectStartW010Request)
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Boolean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                liveData.value = false
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
    /**
     * 上传项目结束信息
     */
    fun postProjectEndW012(projectEndW012Request: ProjectEndW012Request): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_PROJECT_END,
            getIpAddress(),
            getJsonData(projectEndW012Request)
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Boolean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                liveData.value = false
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
    /**
     * 上传文件
     */
    fun postUploadFile(file: File, requestBody: RequestBody): LiveData<String> {
        val liveData = MutableLiveData<String>()
        val call = RetrofitService.create(UploadFile::class.java).upload(
            uploadFile("objFile", file, requestBody)
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    liveData.value = response.body()?.string()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
    /**
     * 触发摄像头拍照
     */
    fun postTakePhoto(takePhotoW009Request: TakePhotoW009Request): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_TAKE_PHOTO,
            getIpAddress(),
            getJsonData(takePhotoW009Request)
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Boolean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                liveData.value = false
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
    /**
     * 查询人工检验项目最短时间
     */
    fun getLeastestTime(ajcx: String, jyxm: String): MutableLiveData<LeastestTimeR019Response> {
        val liveData = MutableLiveData<LeastestTimeR019Response>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_LEASTEST_TIME,
            getIpAddress(),
            getJsonData(LeastestTimeR019Request(ajcx, jyxm))
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Bean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
    /**
     * 开始上线
     */
    fun startOnline(startOnlineW015Request: StartOnlineW015Request): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_START_ONLINE,
            getIpAddress(),
            getJsonData(startOnlineW015Request)
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Boolean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
    /**
     * 获取上线状态信息
     */
    fun getOnlineStatus(onlineStatusR024Request: OnlineStatusR024Request): LiveData<OnlineStatusR024Response> {
        val liveData = MutableLiveData<OnlineStatusR024Response>()

        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_ONLINE_STUTAS,
            getIpAddress(),
            getJsonData(onlineStatusR024Request)
        )

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response != null) {
                    if (response.isSuccessful) {
                        val stringResponse = response.body()?.string()
                        val commonResponse = GsonSingleton.instance
                            .fromJson(stringResponse, CommonResponse::class.java)
                        if (commonResponse.Code == "1") {
                            val beanList = ArrayList<OnlineStatusR024Response>()
                            for (element in commonResponse.Body) {
                                val bodyJson =
                                    GsonSingleton.instance.toJson(element)
                                beanList.add(
                                    GsonSingleton.instance
                                        .fromJson(bodyJson, OnlineStatusR024Response::class.java)
                                )
                            }
                            if (beanList.isNotEmpty()) {
                                liveData.value = beanList[0]
                            }
                        } else {
                            if (commonResponse.Code == null) {
                                Toast.makeText(
                                    MyApp.context,
                                    "${typeOf<OnlineStatusR024Response>()}Code=Null",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    MyApp.context,
                                    commonResponse.Message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(MyApp.context, response.message(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(
                        MyApp.context,
                        "${typeOf<OnlineStatusR024Response>()}response = null",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })

        return liveData
    }
}
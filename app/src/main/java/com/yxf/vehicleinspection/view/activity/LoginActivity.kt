package com.yxf.vehicleinspection.view.activity

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.bean.request.CommonRequest
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.bean.request.UserInfoRequest
import com.yxf.vehicleinspection.databinding.ActivityLoginBinding
import com.yxf.vehicleinspection.service.WriteService
import com.yxf.vehicleinspection.singleton.GsonSingleton
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.IpHelper
import com.yxf.vehicleinspection.viewModel.JsCsCodeViewModel
import com.yxf.vehicleinspection.viewModel.JsCsCodeViewModelFactory
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseBindingActivity<ActivityLoginBinding>() {
    private val TAG = "LoginActivity"
    val viewModel: JsCsCodeViewModel by viewModels { JsCsCodeViewModelFactory(MyApp.jsCsCodeRepository) }

    override fun init() {
        binding.btnLogin.setOnClickListener {
            if (binding.tvUsername.text.toString() == "" || binding.tvPassword.text.toString() == "") {
                Toast.makeText(this, "用户名或密码为空", Toast.LENGTH_SHORT).show()
            } else {
                val service = RetrofitService.create(WriteService::class.java)
                val requestArray = ArrayList<UserInfoRequest>()
                requestArray.add(UserInfoRequest(
                    binding.tvUsername.text.toString(),
                    binding.tvPassword.text.toString(),
                    IpHelper.getIpAddress()))

                val commonRequestString = GsonSingleton.getGson().toJson(CommonRequest(requestArray))
                val call = service.write("LYYDJKW001", IpHelper.getIpAddress(), commonRequestString)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>,
                    ) {
                        if (response.isSuccessful) {
                            //.string只允许调用一次
                            val stringResponse = response.body()?.string()
                            val queryResponse = GsonSingleton.getGson()
                                .fromJson(stringResponse, CommonResponse::class.java)
                            if (queryResponse.Code.equals("1")) {
//                                val userInfoList = ArrayList<UserInfo>()
//                                for (index in 0 until queryResponse.Body?.size!!) {
//                                    val bodyJson =
//                                        GsonSingleton.getGson().toJson(queryResponse.Body[index])
//                                    userInfoList.add(GsonSingleton.getGson()
//                                        .fromJson(bodyJson, UserInfo::class.java))
//                                }

                                Toast.makeText(MyApp.context, "登录成功", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(MyApp.context,
                                    queryResponse.Message,
                                    Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(MyApp.context,
                                response.message(),
                                Toast.LENGTH_LONG).show()
                        }


                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }

        }
    }

}
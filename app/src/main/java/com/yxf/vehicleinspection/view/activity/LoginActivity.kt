package com.yxf.vehicleinspection.view.activity

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import com.permissionx.guolindev.PermissionX
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.base.clickWithTrigger
import com.yxf.vehicleinspection.databinding.ActivityLoginBinding
import com.yxf.vehicleinspection.singleton.SharedP
import com.yxf.vehicleinspection.utils.setVisibility
import com.yxf.vehicleinspection.viewModel.LoginViewModel
import com.yxf.vehicleinspection.viewModel.LoginViewModelFactory
import java.io.File

/**
 * 登录Activity
 */
class LoginActivity : BaseBindingActivity<ActivityLoginBinding>() {
    private val loginViewModel by viewModels<LoginViewModel> { LoginViewModelFactory((application as MyApp).userInfoRepository) }
    override fun init() {
        binding.tvVersion.text = packageManager.getPackageInfo(this.packageName,0).versionName
        //动态请求权限
        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,)
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "已获取所需权限", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,
                        "未获取到: $deniedList",
                        Toast.LENGTH_LONG).show()
                }
            }
        //获取在SP中的用户名
        if (SharedP.instance.getString("username","")!=null && SharedP.instance.getString("username","")!= ""){
            binding.cbRememberUsername.isChecked = true
        }
        binding.tvUsername.setText(SharedP.instance.getString("username", ""))
        //未输入用户名和密码的情况下不能点击登录按钮
        binding.btnLogin.isEnabled = false
        val watcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnLogin.isEnabled = binding.tvUsername.text.toString()
                    .isNotEmpty() && binding.tvPassword.text.toString().isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
        binding.tvUsername.addTextChangedListener(watcher)
        binding.tvPassword.addTextChangedListener(watcher)

        //获取当前App版本号和服务器版本号进行比对，如果版本号不一致则提示强制更新
        loginViewModel.getVersion().observe(this){
            val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                this.packageManager.getPackageInfo(this.packageName,0).longVersionCode
            } else {
                this.packageManager.getPackageInfo(this.packageName,0).versionCode
            }
            val version = this.packageManager.getPackageInfo(this.packageName,0).versionName
            if (!(versionCode.toString() == it.VersionCode && version.toString() == it.Version)){
                AlertDialog.Builder(this)
                    .setMessage("检测到有更新，请更新app")
                    .setPositiveButton("确定"

                    ) { _, _ ->
                        downloadApk("http://${SharedP.instance.getString("ipAddress","192.168.1.1")}:${SharedP.instance.getString("ipPort","80")}/apk/app-release.apk")
                        setVisibility(this,binding.pbLogin,false)

                    }
                    .setCancelable(false)
                    .setTitle("检测更新").create().show()
            }
        }

        // 点击设置按钮
        binding.btnSetting.setOnClickListener {
            startActivity(Intent(this,SettingActivity::class.java))
            finish()
        }

        //点击登录按钮
        binding.btnLogin.clickWithTrigger {
            if (binding.cbRememberUsername.isChecked) {
                SharedP.instance.edit{
                    putString("username", binding.tvUsername.text.toString())
                }

            }else{
                SharedP.instance.edit{
                    putString("username","")
                }

            }

            setVisibility(this,binding.pbLogin,true)

            //调用登录接口
            loginViewModel.getUser(
                binding.tvUsername.text.toString(),
                binding.tvPassword.text.toString()
            ).observe(this){
                val intent = Intent(this,DisplayActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("bean001",it)
                intent.putExtra("user",bundle)
                startActivity(intent)
                finish()
            }
            loginViewModel.isLogin.observe(this){
                setVisibility(this,binding.pbLogin,false)
            }


        }

    }

    /**
     * 使用downloadManager下载文件
     * @param downloadPath 下载地址
     */
    fun downloadApk(downloadPath : String){
        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(downloadPath)
        val request = DownloadManager.Request(uri)
        request.setDestinationInExternalPublicDir("Download","app-release.apk")
        request.setMimeType("application/vnd.android.package-archive")
        request.allowScanningByMediaScanner()
        request.setVisibleInDownloadsUi(true)
        val refernece = downloadManager.enqueue(request)
        val filter  = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        val receive : BroadcastReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                val myDownloadId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1)
                if (refernece == myDownloadId){
                    val downloadFileUri = downloadManager.getUriForDownloadedFile(refernece)
                    if (downloadFileUri!=null){
                        openAPK(downloadFileUri)
                    }
                }
            }
        }
        registerReceiver(receive,filter)
    }
    //打开安装包安装文件
    private fun openAPK(content: Uri) {
        val apkFile = File(content.toString())
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            intent.setDataAndType(content,"application/vnd.android.package-archive")

        }else{
            intent.setDataAndType(Uri.fromFile(apkFile),"application/vnd.android.package-archive")

        }
        startActivity(intent)
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 666 && resultCode == RESULT_OK){
//            downloadApk("http://${SharedP.instance.getString("ipAddress","192.168.1.1")}:${SharedP.instance.getString("ipPort","80")}/apk/app-release.apk")
//        }
//    }



}
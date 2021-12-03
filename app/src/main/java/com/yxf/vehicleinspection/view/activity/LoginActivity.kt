package com.yxf.vehicleinspection.view.activity

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityLoginBinding
import com.yxf.vehicleinspection.singleton.SharedP
import com.yxf.vehicleinspection.viewModel.LoginViewModel
import com.yxf.vehicleinspection.viewModel.LoginViewModelFactory
import java.io.File
import java.io.IOException


class LoginActivity : BaseBindingActivity<ActivityLoginBinding>() {
    private var id: Long = 0L
    private lateinit var downloadManager : DownloadManager
    private val TAG = "LoginActivity"
    private val loginViewModel by viewModels<LoginViewModel> { LoginViewModelFactory((application as MyApp).userInfoRepository) }
    override fun init() {
        if (SharedP.instance.getString("username","")!=null && SharedP.instance.getString("username","")!= ""){
            binding.cbRememberUsername.isChecked = true
        }
        binding.tvUsername.setText(SharedP.instance.getString("username", ""))
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
        loginViewModel.getVersion().observe(this){
            val versionCode = this.packageManager.getPackageInfo(this.packageName,0).versionCode
            val version = this.packageManager.getPackageInfo(this.packageName,0).versionName
            if (!(versionCode.toString() == it.VersionCode && version.toString() == it.Version)){
                AlertDialog.Builder(this)
                    .setMessage("检测到有更新，请更新app")
                    .setPositiveButton("确定"

                    ) { dialog, which ->
                        downloadApk("http://${SharedP.instance.getString("ipAddress","192.168.1.1")}:${SharedP.instance.getString("ipPort","80")}/apk/app-release.apk")


                    }
                    .setCancelable(false)
                    .setTitle("检测更新").create().show()
            }
        }

        binding.btnLogin.setOnClickListener {
            if (binding.cbRememberUsername.isChecked) {
                SharedP.instance.edit().apply {
                    putString("username", binding.tvUsername.text.toString())
                    apply()
                }
            }else{
                SharedP.instance.edit().apply{
                    putString("username","")
                    apply()
                }
            }

            binding.pbLogin.visibility = View.VISIBLE
//            loginViewModel.isLoading(
//                binding.tvUsername.text.toString(),
//                binding.tvPassword.text.toString()).observe(this){
//                if (it) {
////                    binding.pbLogin.visibility = View.GONE
//                    val intent = Intent(this@LoginActivity, DisplayActivity::class.java)
//                    val bundle = Bundle()
//                    bundle.putSerializable("bean001",)
//                    startActivity(intent)
//                    finish()
//                } else {
//                    binding.pbLogin.visibility = View.GONE
//                }
//            }
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
                if (it) {
                } else {
                    binding.pbLogin.visibility = View.GONE
                }
            }


        }

    }
    fun downloadFile(): Long {

        val apkUrl = "http://${SharedP.instance.getString("ipAddress","192.168.1.1")}:${SharedP.instance.getString("ipPort","80")}/apk/app-release.apk"
        val uri = Uri.parse(apkUrl)
        val request = DownloadManager.Request(uri)
        request.setDestinationInExternalPublicDir("Download","app-release.apk")
        id =  downloadManager.enqueue(request)
        return id
    }
    fun installApk(){
        Intent(Intent.ACTION_VIEW).also {
            installIntent ->
            val apkFile : File? = try {
                createApkFile()
            }catch (ex : IOException){
                null
            }
            apkFile?.also {
                val apkUri : Uri = FileProvider.getUriForFile(this,
                    "com.example.android.fileprovider",
                it)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        //Android8.0开始需要获取应用内安装权限
                        val allowInstall: Boolean = this.packageManager.canRequestPackageInstalls()
                        //如果还没有授权安装应用，去设置内开启应用内安装权限
                        if (!allowInstall) {
                            //注意这个是8.0新API
                            val packageUri = Uri.parse("package:$packageName")
                            val intentX = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUri)
                            startActivityForResult(intentX,666)
                            return
                        }
                    }
                    installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }

                installIntent.setDataAndType(apkUri,"application/vnd.android.package-archive")
                installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(installIntent)

            }
        }
    }

    private fun createApkFile(): File {
        val filePath = "${Environment.getExternalStorageDirectory().absolutePath}/Download/app-release.apk"
        Log.e(TAG, "init: $filePath", )
        return File(filePath)
    }

    fun downloadApk(downloadPath : String){
        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        var uri = Uri.parse(downloadPath)
        var request = DownloadManager.Request(uri)
        request.setDestinationInExternalPublicDir("Download","app-release.apk")
        request.setMimeType("application/vnd.android.package-archive")
        request.allowScanningByMediaScanner()
        request.setVisibleInDownloadsUi(true)
        var refernece = downloadManager.enqueue(request)
        var filter : IntentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        var receive : BroadcastReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                var myDownloadId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1)
                if (refernece == myDownloadId){
                    var downloadFileUri = downloadManager.getUriForDownloadedFile(refernece)
                    if (downloadFileUri!=null){
                        openAPK(downloadFileUri)
                    }
                }
            }
        }
        registerReceiver(receive,filter)
    }

    private fun openAPK(content: Uri) {
        var apkFile = File(content.toString())
        var intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(content,"application/vnd.android.package-archive")

        }else{
            intent.setDataAndType(Uri.fromFile(apkFile),"application/vnd.android.package-archive")

        }
        startActivity(intent)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 666 && resultCode == RESULT_OK){
            Log.e(TAG, "onActivityResult: yes", )
            downloadApk("http://${SharedP.instance.getString("ipAddress","192.168.1.1")}:${SharedP.instance.getString("ipPort","80")}/apk/app-release.apk")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}
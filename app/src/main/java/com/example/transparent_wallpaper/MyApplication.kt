package com.example.transparent_wallpaper

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity



class MyApplication : AppCompatActivity() {

//    var PRODUCT_ID_MONTH: String = "android.test.purchased"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_application)

        //AppOpenManager.getInstance().disableAppResumeWithActivity(SplashActivity::class.java)

    }

    fun getAppTokenAdjust(): String {
        return "null"
    }

    fun getFacebookID(): String {
        return "null"
    }

    fun buildDebug(): Boolean {
        return true
    }

    // set id app open resume
    protected fun initAppOpenResume(): String {
        return "ca-app-pub-3940256099942544/9257395921"
    }

    // yêu cầu dùng Adjust thì set = true không dùng thì set = false
    protected fun isSetUpAdjust(): Boolean {
        return true
    }


}
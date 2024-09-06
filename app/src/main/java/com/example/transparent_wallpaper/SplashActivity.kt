package com.example.transparent_wallpaper

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.OnBackPressedCallback

import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Base.BaseViewModel
import com.example.transparent_wallpaper.Screen.Language.LanguageActivity
import com.example.transparent_wallpaper.Utils.SystemUtils
import com.example.transparent_wallpaper.databinding.ActivitySplashBinding
import java.util.Locale


class SplashActivity : BaseActivity<ActivitySplashBinding, BaseViewModel>() {
    override fun createBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun setViewModel() = BaseViewModel()

    companion object {
        var isClearn = true
    }



    override fun initView() {
        super.initView()
        setContentView(R.layout.activity_splash)

        val currentLanguage = Locale.getDefault().language
        Log.d("cccc",currentLanguage)
        SystemUtils.saveDeviceLanguage(this, currentLanguage)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LanguageActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })


//        // chú ý muốn hiện dialog ump thì cần fake ip sang châu âu, và logo của app phải có kích thước 512x512
//        val adsConsentManager = AdsConsentManager(this)
//        adsConsentManager.requestUMP(
//            !AdsConsentManager.getConsentResult(this@SplashActivity)
//        ) { result: Boolean ->
//            // accept ump
//            if (result) {
//                //init sdk
//                Admob.getInstance().initAdmod(this@SplashActivity)
//                //funtion use to show ads splash
//                initShowAdsSplash()
//                // trường hợp project tích hợp với remote config thì cần init remote config sau đó mới initShowAdsSplash()
//            } else {
//                startActivity(Intent(this@SplashActivity, LanguageActivity::class.java))
//                finish()
//            }
//        }
    }
//    private fun initShowAdsSplash() {
//        adsSplash = AdsSplash.init(true, true, "30_70")
//        val idsOpen: MutableList<String> = ArrayList()
//        idsOpen.add("ca-app-pub-3940256099942544/9257395921")
//        val idsInter: MutableList<String> = ArrayList()
//        idsInter.add("ca-app-pub-3940256099942544/1033173712")
//        adsSplash?.showAdsSplash(this@SplashActivity, idsOpen, idsInter, adCallback, interCallback)
//    }
//
//
//    override fun onResume() {
//        if (adsSplash != null) {
//            adsSplash?.onCheckShowSplashWhenFail(this, adCallback, mInterCallback)
//        }
//    }
//
//    // Khai báo adCallback và interCallback
//    private val adCallback = object : Admob.AdCallback {
//        override fun onAdClosed() {
//            // Xử lý khi quảng cáo đóng
//        }
//
//        override fun onAdFailedToLoad(errorCode: Int) {
//            // Xử lý khi quảng cáo không tải được
//        }
//
//        override fun onAdOpened() {
//            // Xử lý khi quảng cáo mở
//        }
//    }
//
//    private val mInterCallback = object : Admob.AdCallback {
//        override fun onAdClosed() {
//            // Xử lý khi quảng cáo xen kẽ đóng
//        }
//
//        override fun onAdFailedToLoad(errorCode: Int) {
//            // Xử lý khi quảng cáo xen kẽ không tải được
//        }
//
//        override fun onAdOpened() {
//            // Xử lý khi quảng cáo xen kẽ mở
//        }
//    }




}
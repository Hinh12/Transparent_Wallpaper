package com.example.transparent_wallpaper

import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.OnBackPressedCallback
import com.amazic.ads.callback.AdCallback
import com.amazic.ads.callback.InterCallback
import com.amazic.ads.util.Admob
import com.amazic.ads.util.AdsSplash
import com.amazic.ads.util.AppOpenManager
import com.amazic.ads.util.manager.banner.BannerBuilder
import com.amazic.ads.util.manager.banner.BannerManager
import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Base.BaseViewModel
import com.example.transparent_wallpaper.Screen.Language.LanguageActivity
import com.example.transparent_wallpaper.Utils.SystemUtils
import com.example.transparent_wallpaper.databinding.ActivitySplashBinding
import com.google.android.gms.ads.interstitial.InterstitialAd
import java.util.Locale

class SplashActivity : BaseActivity<ActivitySplashBinding, BaseViewModel>() {
    private var adsSplashNew: AdsSplash? = null
    private lateinit var bannerManager: BannerManager

    override fun createBinding() = ActivitySplashBinding.inflate(layoutInflater)
    override fun setViewModel() = BaseViewModel()

    private fun startLanguageActivity() {
        startActivity(Intent(this@SplashActivity, LanguageActivity::class.java))
        finish()
    }

    private val adCallBack: AdCallback = object : AdCallback() {
        override fun onNextAction() {
            super.onNextAction()
            startLanguageActivity()
        }
    }

    private val interCallbackNew: InterCallback = object : InterCallback() {
        override fun onNextAction() {
            super.onNextAction()
            startLanguageActivity()
        }

        override fun onAdLoadSuccess(interstitialAd: InterstitialAd?) {
            super.onAdLoadSuccess(interstitialAd)
        }

    }


    override fun initView() {
        super.initView()
        setContentView(R.layout.activity_splash)

        // Lưu ngôn ngữ của thiết bị
        val currentLanguage = Locale.getDefault().language
        Log.d("Language", currentLanguage)
        SystemUtils.saveDeviceLanguage(this, currentLanguage)

        // Vô hiệu hóa nút back
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do nothing
            }
        })

        // Load banner quảng cáo
        val bannerBuilder = BannerBuilder(this, this)
            .initId(listOf(getString(R.string.banner_all))) // ID banner thực tế
        bannerManager = BannerManager(bannerBuilder)
        bannerManager?.setReloadAds()
        initShowAdsSplashNew()
        // Start countdown splash

    }


    private fun initShowAdsSplashNew() {
        Admob.getInstance().setOpenActivityAfterShowInterAds(true)
//        Admob.getInstance().setTimeInterval( RemoteConfig.getConfigLong(
//            baseContext,
//            RemoteConfig.interval_between_interstitial
//        ))
        adsSplashNew = AdsSplash.init(
            true,
            true,
            "30_70"
        )
        var listOp = ArrayList<String>()
        listOp.add(getString(R.string.open_splash))
        var listInter = ArrayList<String>()
        listInter.add(getString(R.string.inter_splash))
        adsSplashNew?.showAdsSplash(
            this,
            listOp,
            listInter,
            adCallBack,
            interCallbackNew
        )
    }

        override fun onResume() {
        super.onResume()
        adsSplashNew?.onCheckShowSplashWhenFail(this, adCallBack, interCallbackNew)
        AppOpenManager.getInstance().disableAppResumeWithActivity(this.javaClass)
    }
}

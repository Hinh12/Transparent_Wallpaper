package com.example.transparent_wallpaper.Screen.Intro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.amazic.ads.callback.AdCallback
import com.amazic.ads.callback.InterCallback
import com.amazic.ads.util.Admob
import com.amazic.ads.util.AdsConsentManager
import com.amazic.ads.util.AdsSplash
import com.amazic.ads.util.AppOpenManager
import com.amazic.ads.util.manager.banner.BannerBuilder
import com.amazic.ads.util.manager.banner.BannerManager
import com.amazic.ads.util.manager.native_ad.NativeBuilder
import com.amazic.ads.util.manager.native_ad.NativeManager
import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Base.BaseViewModel
import com.example.transparent_wallpaper.MainActivity
import com.example.transparent_wallpaper.Model.Intro
import com.example.transparent_wallpaper.R
import com.example.transparent_wallpaper.Screen.Home.HomeActivity
import com.example.transparent_wallpaper.Screen.Language.LanguageActivity
import com.example.transparent_wallpaper.Utils.CheckInternet
import com.example.transparent_wallpaper.databinding.ActivityIntro1Binding
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class Intro1Activity : BaseActivity<ActivityIntro1Binding, BaseViewModel>() {

    private var nativeManager: NativeManager? = null
    private lateinit var listItemSlide: List<Intro>
    private var adsSplashNew: AdsSplash? = null
    private lateinit var bannerManager: BannerManager
    override fun createBinding() = ActivityIntro1Binding.inflate(layoutInflater)

    override fun setViewModel() = BaseViewModel()

    private fun HomeActivity() {
        startActivity(Intent(this@Intro1Activity, HomeActivity::class.java))
        finish()
    }
    private val adCallBack: AdCallback = object : AdCallback() {
        override fun onNextAction() {
            super.onNextAction()
            HomeActivity()
        }
    }

    private val interCallbackNew: InterCallback = object : InterCallback() {
        override fun onNextAction() {
            super.onNextAction()
            HomeActivity()
        }

        override fun onAdLoadSuccess(interstitialAd: InterstitialAd?) {
            super.onAdLoadSuccess(interstitialAd)
        }

    }



    override fun initView() {
        super.initView()

        loadNative(
            listOf("ca-app-pub-3940256099942544/2247696110"),
            binding.nativeAds,
            R.layout.ads_native_shimer_small,
            R.layout.ads_native_intro_small2
        )
        listItemSlide = arrayListOf(
            Intro(
                R.drawable.img_intro1,
                R.string.transparency,
                R.string.reveal_your_screens_beauty
            ),
            Intro(R.drawable.img_intro2, R.string.crystal, R.string.clarity_and_style),
            Intro(
                R.drawable.img_intro03,
                R.string.clearvision,
                R.string.elevate_your_screens_allure
            )
        )



        val viewPagerIntro: ViewPager2 = findViewById(R.id.view_paper2)
        val dotsIndicator: DotsIndicator = findViewById(R.id.dots_indicator)


        viewPagerIntro.adapter = AdapterSlideIntro(listItemSlide, viewPagerIntro, this)
        dotsIndicator.attachTo(viewPagerIntro)



        findViewById<View>(R.id.txt_intro_Next).setOnClickListener {
            val currentItem = viewPagerIntro.currentItem
            if (currentItem < listItemSlide.size - 1) {
                viewPagerIntro.currentItem = currentItem + 1
            } else {
                initShowAdsSplashNew()
            }
        }
    }



    private fun initShowAdsSplashNew() {
        adsSplashNew = AdsSplash.init(
            true,
            true,
            "30_70"
        )
        var listOp = ArrayList<String>()
        listOp.add(getString(R.string.inter_intro))
        var listInter = ArrayList<String>()
        listInter.add(getString(R.string.inter_intro))
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
    }


    fun loadNative(listId: List<String?>?, frAds: FrameLayout, shimmer: Int, layoutNative: Int) {
        if (AdsConsentManager.getConsentResult(this)) {
            val nativeBuilder = NativeBuilder(this, frAds, shimmer, layoutNative)
            nativeBuilder.setListIdAd(listId)
            nativeManager = NativeManager(
                this,
                this, nativeBuilder
            )
        } else {
            frAds.visibility = View.GONE
            frAds.removeAllViews()
        }
    }


}

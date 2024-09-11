package com.example.transparent_wallpaper.Screen.HDWallpaper

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.amazic.ads.util.AdsConsentManager
import com.amazic.ads.util.manager.native_ad.NativeBuilder
import com.amazic.ads.util.manager.native_ad.NativeManager
import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Base.BaseViewModel
import com.example.transparent_wallpaper.R
import com.example.transparent_wallpaper.Screen.Home.HomeActivity
import com.example.transparent_wallpaper.databinding.ActivitySuccessBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class SuccessActivity : BaseActivity<ActivitySuccessBinding,BaseViewModel>() {

    private var nativeManager: NativeManager? = null
    private var mInterstitialAd: InterstitialAd? = null
    override fun createBinding() = ActivitySuccessBinding.inflate(layoutInflater)

    override fun setViewModel() = BaseViewModel()
    override fun initView() {
        super.initView()


        // Load Interstitial Ad
        loadInterstitialAd()

        // Ẩn thanh điều hướng
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let { controller ->
                controller.hide(WindowInsetsCompat.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )

        }

        binding.btnDone.setOnClickListener {
            showInterstitialAd()
        }


        val list =  ArrayList<String>()
        list.add("ca-app-pub-3940256099942544/2247696110")
        loadNative(
            list,
            binding.nativeAds,
            R.layout.ads_native_shimer_large,
            R.layout.ads_native_language_start
        )
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


    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",  // Thay bằng ID quảng cáo của bạn
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }

                override fun onAdFailedToLoad(adError: com.google.android.gms.ads.LoadAdError) {
                    mInterstitialAd = null
                }
            }
        )
    }

    private fun showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    // Quảng cáo bị tắt, chuyển tới màn hình Home
                    navigateToHome()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    // Nếu quảng cáo không hiển thị, chuyển tới màn hình Home
                    navigateToHome()
                }
            }
            mInterstitialAd?.show(this)
        } else {
            // Nếu quảng cáo không sẵn sàng, chuyển tới màn hình Home
            navigateToHome()
        }
    }


    private fun navigateToHome() {
        startActivity(
            Intent(
                this@SuccessActivity,
                HomeActivity::class.java
            )
        )
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }


    override fun onStop() {
        super.onStop()
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }


}
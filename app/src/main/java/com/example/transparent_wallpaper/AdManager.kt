package com.example.transparent_wallpaper

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

class AdManager(private val context: Context) {

    private var appOpenAd: AppOpenAd? = null
    private var loadTime: Long = 0

    fun loadAd() {
        AppOpenAd.load(
            context,
            "ca-app-pub-3940256099942544/9257395921", // Thay bằng ID quảng cáo của bạn
            AdRequest.Builder().build(),
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad
                    loadTime = System.currentTimeMillis()
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.e("AppOpenAdManager", "Failed to load app open ad: ${adError.message}")
                }
            }
        )
    }
    private fun isAppOpenAdLoaded(): Boolean {
        // Trả về true nếu quảng cáo đã được tải, false nếu không
        return appOpenAd != null
    }


    fun showAdIfAvailable(activity: Activity) {
        if (appOpenAd != null) {
            appOpenAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    appOpenAd = null
                    loadAd() // Reload the ad
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    appOpenAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    // Do nothing
                }
            }
            appOpenAd?.show(activity)
        } else {
            loadAd() // Reload if ad is not available
        }
    }
}
package com.example.transparent_wallpaper

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.amazic.ads.util.AdsApplication
import com.amazic.ads.util.AppOpenManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.appopen.AppOpenAd


class MyApplication : AdsApplication(), DefaultLifecycleObserver {

    override fun onCreate() {
        super<AdsApplication>.onCreate()
        AppOpenManager.getInstance().disableAppResumeWithActivity(SplashActivity::class.java)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)



        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                // Handle activity creation
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }
    override fun getAppTokenAdjust(): String {
        return ""
    }
    override fun getFacebookID(): String {
        return ""
    }
    override fun buildDebug(): Boolean {
        return true
    }
    override fun initAppOpenResume(): String {
        return getString(R.string.open_splash)
    }
    override fun isSetUpAdjust(): Boolean {
        return true
    }


}
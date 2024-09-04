package com.example.transparent_wallpaper

import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Base.BaseViewModel
import com.example.transparent_wallpaper.Screen.Intro.Intro1Activity
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

    }


}
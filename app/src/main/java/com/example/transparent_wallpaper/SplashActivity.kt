package com.example.transparent_wallpaper

import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Base.BaseViewModel
import com.example.transparent_wallpaper.Screen.Intro.Intro1Activity
import com.example.transparent_wallpaper.Screen.Language.LanguageActivity
import com.example.transparent_wallpaper.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding, BaseViewModel>() {
    override fun createBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun setViewModel() = BaseViewModel()

    override fun initView() {
        super.initView()
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LanguageActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }


}
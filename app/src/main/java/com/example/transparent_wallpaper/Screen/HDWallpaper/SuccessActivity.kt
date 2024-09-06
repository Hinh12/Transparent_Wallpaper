package com.example.transparent_wallpaper.Screen.HDWallpaper

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Base.BaseViewModel
import com.example.transparent_wallpaper.R
import com.example.transparent_wallpaper.Screen.Home.HomeActivity
import com.example.transparent_wallpaper.databinding.ActivitySuccessBinding

class SuccessActivity : BaseActivity<ActivitySuccessBinding,BaseViewModel>() {
    override fun createBinding() = ActivitySuccessBinding.inflate(layoutInflater)

    override fun setViewModel() = BaseViewModel()
    override fun initView() {
        super.initView()
        val btnDone = findViewById<Button>(R.id.btn_done);

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

        btnDone.setOnClickListener {
            startActivity(
                Intent(
                    this@SuccessActivity,
                    HomeActivity::class.java
                )
            )
        }

    }


}
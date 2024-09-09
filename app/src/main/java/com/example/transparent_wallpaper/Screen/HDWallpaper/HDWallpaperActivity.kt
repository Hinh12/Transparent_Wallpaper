package com.example.transparent_wallpaper.Screen.HDWallpaper

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Base.BaseViewModel
import com.example.transparent_wallpaper.Model.HdWallpaperModel
import com.example.transparent_wallpaper.R
import com.example.transparent_wallpaper.Screen.Home.HomeActivity
import com.example.transparent_wallpaper.Screen.SetWallpaper.SetWallpaperActivity
import com.example.transparent_wallpaper.databinding.ActivityHdwallpaperBinding

class HDWallpaperActivity : BaseActivity<ActivityHdwallpaperBinding, BaseViewModel>() {

    private lateinit var adapter: HDWallpaperAdapter
    private lateinit var list: List<HdWallpaperModel>
    private var currentPosition: Int = -1


    override fun createBinding() = ActivityHdwallpaperBinding.inflate(layoutInflater)

    override fun setViewModel() = BaseViewModel()

    override fun initView() {
        super.initView()

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )

        binding.imgBackk.setOnClickListener {
            navigateTo(HomeActivity::class.java)
        }

        val tvTitle_hdwallpaper = findViewById<TextView>(R.id.tvTitle_hdwallpaper)
        tvTitle_hdwallpaper.isSelected = true

        list = listOf(
            HdWallpaperModel(1, R.drawable.img_content_1),
            HdWallpaperModel(2, R.drawable.img_content_04),
            HdWallpaperModel(3, R.drawable.img_content_2),
            HdWallpaperModel(4, R.drawable.img_content_5),
            HdWallpaperModel(5, R.drawable.img_content_3),
            HdWallpaperModel(6, R.drawable.img_content_6),
            HdWallpaperModel(7, R.drawable.img_content_5),
            HdWallpaperModel(8, R.drawable.img_content_4),
            HdWallpaperModel(9, R.drawable.img_content_2),
            HdWallpaperModel(10, R.drawable.img_content_3)
        )

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rcvHdWallpaper.layoutManager = layoutManager

        // Initialize the adapter with a lambda for item click listener
        adapter = HDWallpaperAdapter(this, list) { model ->
//            navigateToAddPosition(SetWallpaperActivity::class.java, model.idImage)
            navigateToSetWallpaperActivity(model.idImage)
        }

        binding.rcvHdWallpaper.adapter = adapter

    }

    private fun navigateTo(targetClass: Class<*>) {
        val intent = Intent(this, targetClass)
        startActivity(intent)
    }

    private fun navigateToAddPosition(targetClass: Class<*>, position: Int) {
        val intent = Intent(this, targetClass)
        intent.putExtra("POSITION", position)
        startActivity(intent)
    }

    private fun navigateToSetWallpaperActivity(imageId: Int) {
        val intent = Intent(this, SetWallpaperActivity::class.java)
        intent.putExtra("IMAGE_ID", imageId)
        startActivity(intent)
    }

}
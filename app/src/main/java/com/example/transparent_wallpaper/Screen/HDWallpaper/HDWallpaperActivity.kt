package com.example.transparent_wallpaper.Screen.HDWallpaper

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.amazic.ads.callback.AdCallback
import com.amazic.ads.callback.InterCallback
import com.amazic.ads.util.Admob
import com.amazic.ads.util.AdsSplash
import com.amazic.ads.util.AppOpenManager
import com.amazic.ads.util.manager.banner.BannerManager
import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Base.BaseViewModel
import com.example.transparent_wallpaper.Model.HdWallpaperModel
import com.example.transparent_wallpaper.R
import com.example.transparent_wallpaper.Screen.Home.HomeActivity
import com.example.transparent_wallpaper.Screen.SetWallpaper.SetWallpaperActivity
import com.example.transparent_wallpaper.databinding.ActivityHdwallpaperBinding
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.interstitial.InterstitialAd

class HDWallpaperActivity : BaseActivity<ActivityHdwallpaperBinding, BaseViewModel>() {

    private lateinit var adapter: HDWallpaperAdapter
    private lateinit var list: List<HdWallpaperModel>
    private var adsSplashNew: AdsSplash? = null
    private var adView: AdView? = null


    override fun createBinding() = ActivityHdwallpaperBinding.inflate(layoutInflater)

    override fun setViewModel() = BaseViewModel()
    private fun HDWallActivity() {
        startActivity(Intent(this@HDWallpaperActivity, SetWallpaperActivity::class.java))
        finish()
    }

    private val adCallBack: AdCallback = object : AdCallback() {
        override fun onNextAction() {
            super.onNextAction()
            HDWallActivity()
        }
    }

    private val interCallbackNew: InterCallback = object : InterCallback() {
        override fun onNextAction() {
            super.onNextAction()
            HDWallActivity()
        }

        override fun onAdLoadSuccess(interstitialAd: InterstitialAd?) {
            super.onAdLoadSuccess(interstitialAd)
        }

    }

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
        // Load banner quảng cáo
        val listID: MutableList<String?> = ArrayList()  // Xác định rõ kiểu dữ liệu là String
        listID.add(getString(R.string.admob_Collapsible_id))
        val admob = Admob.getInstance() ?: return
// Tải banner có thể thu gọn ở phía dưới màn hình
        admob.loadCollapsibleBannerFloor(this@HDWallpaperActivity, listID, "bottom")
// Tải banner có thể thu gọn với chức năng tự động tải lại
        admob.loadCollapsibleBannerFloorWithReload(this, listID, lifecycle)



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
            navigateToSetWallpaperActivity(model.idImage)
        }
        binding.rcvHdWallpaper.adapter = adapter


    }

    private fun initShowAdsSplashNew(imageId: Int) {
        Admob.getInstance().setOpenActivityAfterShowInterAds(true)
        adsSplashNew = AdsSplash.init(
            true,
            true,
            "30_70"
        )
        val listOp = ArrayList<String>()
        listOp.add(getString(R.string.open_splash))
        val listInter = ArrayList<String>()
        listInter.add(getString(R.string.inter_splash))
        adsSplashNew?.showAdsSplash(
            this,
            listOp,
            listInter,
            object : AdCallback() {
                override fun onNextAction() {
                    super.onNextAction()
                    val intent = Intent(this@HDWallpaperActivity, SetWallpaperActivity::class.java)
                    intent.putExtra("IMAGE_ID", imageId)
                    startActivity(intent)
                }
            },
            interCallbackNew
        )
    }

    override fun onResume() {
        super.onResume()
        adsSplashNew?.onCheckShowSplashWhenFail(this, adCallBack, interCallbackNew)
        AppOpenManager.getInstance().disableAppResumeWithActivity(this.javaClass)
        Log.d("TAG123", "onResume: ")
    }

    private fun navigateTo(targetClass: Class<*>) {
        val intent = Intent(this, targetClass)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        adView?.destroy()
    }


    private fun navigateToSetWallpaperActivity(imageId: Int) {
        initShowAdsSplashNew(imageId)
    }

}
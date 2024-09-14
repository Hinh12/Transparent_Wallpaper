package com.example.transparent_wallpaper.Screen.SetWallpaper

import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.amazic.ads.callback.AdCallback
import com.amazic.ads.callback.InterCallback
import com.amazic.ads.util.AdsConsentManager
import com.amazic.ads.util.AppOpenManager
import com.amazic.ads.util.manager.banner.BannerBuilder
import com.amazic.ads.util.manager.banner.BannerManager
import com.amazic.ads.util.manager.native_ad.NativeBuilder
import com.amazic.ads.util.manager.native_ad.NativeManager
import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Base.BaseViewModel
import com.example.transparent_wallpaper.Model.HdWallpaperModel
import com.example.transparent_wallpaper.R
import com.example.transparent_wallpaper.Screen.HDWallpaper.HDWallpaperActivity
import com.example.transparent_wallpaper.Screen.HDWallpaper.SuccessActivity
import com.example.transparent_wallpaper.ViewModel.CustomDotsIndicator
import com.example.transparent_wallpaper.ads.InterManage
import com.example.transparent_wallpaper.databinding.ActivitySetWallpaperBinding
import com.example.transparent_wallpaper.databinding.DialogChooseScreenBinding
import com.google.android.gms.ads.interstitial.InterstitialAd
import java.io.IOException

class SetWallpaperActivity : BaseActivity<ActivitySetWallpaperBinding, BaseViewModel>() {


    private lateinit var list: List<HdWallpaperModel>
    private var bannerManager: BannerManager? = null
    private var nativeManager: NativeManager? = null


    override fun createBinding() = ActivitySetWallpaperBinding.inflate(layoutInflater)

    override fun setViewModel() = BaseViewModel()

    override fun viewModel() {}

    private fun SetWallActivity() {
        startActivity(Intent(this@SetWallpaperActivity, HDWallpaperActivity::class.java))
        finish()
    }
    private val adCallBack: AdCallback = object : AdCallback() {
        override fun onNextAction() {
            super.onNextAction()
            SetWallActivity()
        }
    }

    private val interCallbackNew: InterCallback = object : InterCallback() {
        override fun onNextAction() {
            super.onNextAction()
            SetWallActivity()
        }

        override fun onAdLoadSuccess(interstitialAd: InterstitialAd?) {
            super.onAdLoadSuccess(interstitialAd)
        }

    }

    override fun initView() {
        super.initView()


        binding = ActivitySetWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tvTitle_sethdwallpaper = findViewById<TextView>(R.id.tvTitle_sethdwallpaper)
        tvTitle_sethdwallpaper.isSelected = true

        list = listOf(
            HdWallpaperModel(1, R.drawable.img_content_1),
            HdWallpaperModel(2, R.drawable.img_content_4),
            HdWallpaperModel(3, R.drawable.img_content_2),
            HdWallpaperModel(4, R.drawable.img_content_5),
            HdWallpaperModel(5, R.drawable.img_content_3),
            HdWallpaperModel(6, R.drawable.img_content_6),
            HdWallpaperModel(7, R.drawable.img_content_5),
            HdWallpaperModel(8, R.drawable.img_content_4),
            HdWallpaperModel(9, R.drawable.img_content_2),
            HdWallpaperModel(10, R.drawable.img_content_3)
        )

        val adapter = SetWallViewPager2Adapter(list, binding.viewpage2SetWallpaper)
        binding.viewpage2SetWallpaper.adapter = adapter

        // Khởi tạo và sử dụng CustomDotsIndicator
        val customDotsIndicator = CustomDotsIndicator(binding.customDotsIndicator, list.size, this)
        customDotsIndicator.updateDots(0)

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(pageMarginPx))
            addTransformer { page, position ->
                val scale = 1 - kotlin.math.abs(position) * 0.1f
                page.scaleY = scale
            }
        }

        binding.viewpage2SetWallpaper.setPageTransformer(compositePageTransformer)
        binding.viewpage2SetWallpaper.setPadding(offsetPx, 0, offsetPx, 0)
        binding.viewpage2SetWallpaper.clipToPadding = false
        binding.viewpage2SetWallpaper.clipChildren = false
        binding.viewpage2SetWallpaper.offscreenPageLimit = 3
        binding.viewpage2SetWallpaper.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        // Set the current item based on the intent extra
        val position = intent.getIntExtra("position", 0)
        binding.viewpage2SetWallpaper.setCurrentItem(position)
        // Set up button click listener
        binding.btnSetWallpaper.setOnClickListener {
            showDialogChoose()
        }
        // Set up back button listener
        binding.imgBackT.setOnClickListener {
            startActivity(Intent(this, HDWallpaperActivity::class.java))
        }
        // Nhận IMAGE_ID từ intent
        val imageId = intent.getIntExtra("IMAGE_ID", -1)
        // Tìm ảnh trong danh sách dựa trên ID
        val selectedModel = list.find { it.idImage == imageId }
        selectedModel?.let {
            // Hiển thị ảnh trong ViewPager2
            val position = list.indexOf(it)
            val adapter = SetWallViewPager2Adapter(list, binding.viewpage2SetWallpaper)
            binding.viewpage2SetWallpaper.adapter = adapter
            binding.viewpage2SetWallpaper.setCurrentItem(position, false)
        } ?: run {
            // Xử lý nếu không tìm thấy ảnh phù hợp
            //Toast.makeText(this, "Không tìm thấy ảnh phù hợp", Toast.LENGTH_SHORT).show()
        }
        // Cập nhật dot khi trang thay đổi
        binding.viewpage2SetWallpaper.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                customDotsIndicator.updateDots(position)
            }
        })


        InterManage.loadInterAll(this@SetWallpaperActivity)
        try {

            val list: MutableList<String> = ArrayList()
            list.add(getString(R.string.native_language))
            val builder = NativeBuilder(
                this, binding.nativeAdsSetwallpaper,
                R.layout.ads_native_shimmer_setwp, R.layout.ads_native_layout_setwp
            )
            builder.setListIdAd(list)
            nativeManager = NativeManager(this, this, builder)


        } catch (e: Exception) {
            e.printStackTrace()
            binding.nativeAdsSetwallpaper.removeAllViews()
            binding.nativeAdsSetwallpaper.setVisibility(View.INVISIBLE)
        }
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
    private fun showDialogChoose() {
        val dialogChooseScreenBinding = DialogChooseScreenBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
            .setView(dialogChooseScreenBinding.root)
        val dialog = builder.create() // Create and store AlertDialog into variable
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        
        dialogChooseScreenBinding.cstLockScreen.setOnClickListener {
            dialogChooseScreenBinding.imgradioLockScreen.visibility = View.VISIBLE
            dialogChooseScreenBinding.imgradioHomeScreen.visibility = View.GONE
            dialogChooseScreenBinding.imgradioBothScreen.visibility = View.GONE
        }

        dialogChooseScreenBinding.cstHomeScreen.setOnClickListener {
            dialogChooseScreenBinding.imgradioLockScreen.visibility = View.GONE
            dialogChooseScreenBinding.imgradioHomeScreen.visibility = View.VISIBLE
            dialogChooseScreenBinding.imgradioBothScreen.visibility = View.GONE
        }

        dialogChooseScreenBinding.cstBothScreen.setOnClickListener {
            dialogChooseScreenBinding.imgradioLockScreen.visibility = View.GONE
            dialogChooseScreenBinding.imgradioHomeScreen.visibility = View.GONE
            dialogChooseScreenBinding.imgradioBothScreen.visibility = View.VISIBLE
        }

        dialogChooseScreenBinding.btnSelectOptions.setOnClickListener {
            val currentItem = binding.viewpage2SetWallpaper.currentItem
            val currentModel = list[currentItem]

            val drawable = resources.getDrawable(currentModel.imageUrl) as BitmapDrawable
            val bitmap = drawable.bitmap
            val wallpaperManager = WallpaperManager.getInstance(this@SetWallpaperActivity)
            try {
                when {
                    dialogChooseScreenBinding.imgradioLockScreen.visibility == View.VISIBLE -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            wallpaperManager.setBitmap(
                                bitmap,
                                null,
                                true,
                                WallpaperManager.FLAG_LOCK
                            )
                            startActivity(
                                Intent(
                                    this@SetWallpaperActivity,
                                    SuccessActivity::class.java
                                )
                            )
                        } else {

                        }
                    }

                    dialogChooseScreenBinding.imgradioHomeScreen.visibility == View.VISIBLE -> {
                        wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                        startActivity(
                            Intent(
                                this@SetWallpaperActivity,
                                SuccessActivity::class.java
                            )
                        )
                    }

                    dialogChooseScreenBinding.imgradioBothScreen.visibility == View.VISIBLE -> {
                        // Đặt hình nền cho màn hình chính
                        wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)

                        // Đặt hình nền cho màn hình khóa nếu có hỗ trợ
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            wallpaperManager.setBitmap(
                                bitmap,
                                null,
                                true,
                                WallpaperManager.FLAG_LOCK
                            )
                        }
                        startActivity(
                            Intent(
                                this@SetWallpaperActivity,
                                SuccessActivity::class.java
                            )
                        )
                    }

                    else -> {
                    }
                }
                dialog.dismiss() // Close dialog after processing
            } catch (e: IOException) {
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

}
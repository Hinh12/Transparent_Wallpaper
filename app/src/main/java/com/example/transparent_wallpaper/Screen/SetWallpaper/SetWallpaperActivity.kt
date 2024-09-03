package com.example.transparent_wallpaper.Screen.SetWallpaper

import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Base.BaseViewModel
import com.example.transparent_wallpaper.Model.HdWallpaperModel
import com.example.transparent_wallpaper.R
import com.example.transparent_wallpaper.Screen.HDWallpaper.HDWallpaperActivity
import com.example.transparent_wallpaper.Screen.HDWallpaper.SuccessActivity
import com.example.transparent_wallpaper.ViewModel.CustomDotsIndicator
import com.example.transparent_wallpaper.databinding.ActivitySetWallpaperBinding
import com.example.transparent_wallpaper.databinding.DialogChooseScreenBinding
import java.io.IOException

class SetWallpaperActivity : BaseActivity<ActivitySetWallpaperBinding, BaseViewModel>() {


    private lateinit var list: List<HdWallpaperModel>
    private lateinit var nativeFrameAds: FrameLayout


    override fun createBinding() = ActivitySetWallpaperBinding.inflate(layoutInflater)

    override fun setViewModel() = BaseViewModel()

    override fun viewModel() {}

    override fun initView() {
        super.initView()
        //setContentView(R.layout.activity_set_wallpaper)


        binding = ActivitySetWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.imgBackT.setOnClickListener {
            navigateTo(HDWallpaperActivity::class.java)
        }

        val adapter = SetWallViewPager2Adapter(list, binding.viewpage2SetWallpaper)
        binding.viewpage2SetWallpaper.adapter = adapter

        // Set up dots indicator
        val customDotsIndicator = CustomDotsIndicator(binding.customDotsIndicator, list.size, this)
        customDotsIndicator.updateDots(0)
        binding.viewpage2SetWallpaper.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                customDotsIndicator.updateDots(position)
            }
        })

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
//            Toast.makeText(this, "Không tìm thấy ảnh phù hợp", Toast.LENGTH_SHORT).show()
        }

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




    private fun showDialogChoose() {
        val dialogChooseScreenBinding = DialogChooseScreenBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
            .setView(dialogChooseScreenBinding.root)
        val dialog = builder.create() // Create and store AlertDialog into variable

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        // Handle screen selection
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
//                            Toast.makeText(
//                                this@SetWallpaperActivity,
//                                R.string.lock_screen_wallpaper_set_successfully,
//                                Toast.LENGTH_SHORT
//                            ).show()
                            startActivity(
                                Intent(
                                    this@SetWallpaperActivity,
                                    SuccessActivity::class.java
                                )
                            )
                        } else {
//                            Toast.makeText(
//                                this@SetWallpaperActivity,
//                                "Setting lock screen wallpaper requires Android 7.0 or higher",
//                                Toast.LENGTH_SHORT
//                            ).show()
                        }
                    }

                    dialogChooseScreenBinding.imgradioHomeScreen.visibility == View.VISIBLE -> {
                        wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
//                        Toast.makeText(
//                            this@SetWallpaperActivity,
//                            "Home screen wallpaper set successfully",
//                            Toast.LENGTH_SHORT
//                        ).show()
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
                            wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                        }

//                        Toast.makeText(
//                            this@SetWallpaperActivity,
//                            "Both home and lock screen wallpapers set successfully",
//                            Toast.LENGTH_SHORT
//                        ).show()
                        startActivity(
                            Intent(
                                this@SetWallpaperActivity,
                                SuccessActivity::class.java
                            )
                        )
                    }

                    else -> {
//                        Toast.makeText(
//                            this@SetWallpaperActivity,
//                            "Please select screen",
//                            Toast.LENGTH_SHORT
//                        ).show()
                    }
                }
                dialog.dismiss() // Close dialog after processing
            } catch (e: IOException) {
//                Toast.makeText(
//                    this@SetWallpaperActivity,
//                    "Failed to set wallpaper",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
        }
    }


}
package com.example.transparent_wallpaper.Screen.Home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Model.Rate
import com.example.transparent_wallpaper.R
import com.example.transparent_wallpaper.Screen.HDWallpaper.HDWallpaperActivity
import com.example.transparent_wallpaper.Screen.Language.LanguageActivity
import com.example.transparent_wallpaper.Screen.Setting.SettingLanguageActivity
import com.example.transparent_wallpaper.ViewModel.HomeViewModel
import com.example.transparent_wallpaper.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.play.core.review.ReviewManagerFactory

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var nativeAds: FrameLayout
    private var check = false
    private lateinit var sharedPreferences: SharedPreferences


    override fun createBinding() = ActivityHomeBinding.inflate(layoutInflater)
    override fun setViewModel() = HomeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        if (sharedPreferences.getBoolean("isRated", false)) {
            hideRateMenuItem()
        }
        drawerLayout = findViewById(R.id.drawer_layout_home)
        val navView: NavigationView = findViewById(R.id.nav)
        val tpToolbar = findViewById<TextView>(R.id.txtToolBar)
        val hdWallPaper = findViewById<FrameLayout>(R.id.hdWallPaper)


        // Set màu gradient cho text trên toolbar
        tpToolbar.setTextColor(Color.WHITE)
        tpToolbar.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        // Tạo LinearGradient cho TextView
        val width = tpToolbar.paint.measureText(getString(R.string.transparent_wallpaper))
        val textShader = LinearGradient(
            0f, 0f, width, tpToolbar.textSize,
            intArrayOf(
                Color.parseColor("#6A41FB"),
                Color.parseColor("#8348D1"),
                Color.parseColor("#9B4EA8"),
                Color.parseColor("#AB538D"),
                Color.parseColor("#C45963"),
                Color.parseColor("#FF6900"),
                Color.parseColor("#FF6900"),
            ), null, Shader.TileMode.CLAMP
        )

        tpToolbar.paint.shader = textShader

        // Xử lý sự kiện menu
        navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            if (item.itemId == R.id.nav_language && !check) {
                check = true
                navigateTo(SettingLanguageActivity::class.java)
            } else if (item.itemId == R.id.nav_rate && !check) {
                check = true
                showRateDialog()
            } else if (item.itemId == R.id.nav_share && !check) {
                check = true
                share()
            } else if (item.itemId == R.id.nav_feedback) {

            } else if (item.itemId == R.id.nav_policy && !check) {
                check = true
                openPrivacyPolicy()
            }

            binding.drawerLayoutHome.closeDrawer(GravityCompat.START)
            false
        })


        // Mở menu khi bấm vào nút
        val exploreImageView: ImageView = findViewById(R.id.img_menu)
        exploreImageView.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)


        }

        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerOpened(drawerView: View) {

            }

            override fun onDrawerClosed(drawerView: android.view.View) {
                check = false
            }

            override fun onDrawerStateChanged(newState: Int) {

            }

        })



        hdWallPaper.setOnClickListener {
            if (!check) {
                navigateTo(HDWallpaperActivity::class.java)
                check = true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openPrivacyPolicy() {
        val privacyPolicyUrl =
            "https://viblo.asia/p/vong-doi-cua-activity-va-fragment-trong-android-6J3ZgRdWKmB"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(privacyPolicyUrl))
        startActivity(intent)
    }

    private fun share() {
        val intentShare = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.app_name) + "https://play.google.com/store/apps/details?id=$packageName"
            )
        }
        startActivity(Intent.createChooser(intentShare, "Share"))
    }

    private fun showRateDialog() {
        check = true

        val ratingDialog = RatingDialog(this)
        ratingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        ratingDialog.init(object : RatingDialog.OnPress {
            override fun send(s: Int) {
                Rate.isRated()
                Toast.makeText(
                    this@HomeActivity,
                    "Thank you",
                    Toast.LENGTH_SHORT
                ).show()
                ratingDialog.dismiss()
                hideRateMenuItem()
                handleRating(s)
                check = false
            }

            override fun rating(s: Int) {
                Rate.setRated(true)
                Toast.makeText(
                    this@HomeActivity,
                    "Thank you",
                    Toast.LENGTH_SHORT
                ).show()
                ratingDialog.dismiss()
                handleRating(s)
                hideRateMenuItem()
                check = false
            }

            override fun cancel() {
                ratingDialog.dismiss()
                check = false
            }

            override fun later() {
                ratingDialog.dismiss()
                check = false
            }

            override fun gotIt() {
                ratingDialog.dismiss()
                check = false
            }
        })

        ratingDialog.show()
        ratingDialog.setOnDismissListener {
            check = false
            if (Rate.isRated()) {
                hideRateMenuItem()
            }
        }
    }

    private fun handleRating(stars: Int) {
        if (stars >= 4) {
            // User rated 4 or 5 stars
            Rate.setRated(true)
            onRateAppNew()  // Điều hướng trực tiếp đến Google Play
            sharedPreferences.edit().putBoolean("isRated", true).apply()
        } else {
            // User rated 1, 2, or 3 stars
            showThankYouPopup()
        }
    }

    private fun onRateAppNew() {
        val manager = ReviewManagerFactory.create(this)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                val flow = manager.launchReviewFlow(this, reviewInfo!!)
                flow.addOnCompleteListener {
                    // Sau khi hoàn tất luồng đánh giá, điều hướng người dùng đến Google Play
                    rateAppOnStoreNew()
                }
            } else {
                // Nếu không thành công, điều hướng người dùng trực tiếp đến Google Play
                rateAppOnStoreNew()
            }
        }
    }


    private fun rateAppOnStoreNew() {
        val packageName = packageName
        val uri = Uri.parse("market://details?id=$packageName")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }

    private fun showThankYouPopup() {
        // Show a custom thank you popup here
        Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show()
    }


    private fun hideRateMenuItem() {
        val navView: NavigationView = findViewById(R.id.nav)
        val rateMenuItem = navView.menu.findItem(R.id.nav_rate)
        rateMenuItem?.isVisible = false
    }

    private fun navigateTo(targetClass: Class<*>) {
        val intent = Intent(this, targetClass)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onResume() {
        super.onResume()
        check = false
    }
}

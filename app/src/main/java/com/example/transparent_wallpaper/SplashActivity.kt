package com.example.transparent_wallpaper

import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import com.amazic.ads.callback.AdCallback
import com.amazic.ads.callback.InterCallback
import com.amazic.ads.util.Admob
import com.amazic.ads.util.AdsSplash
import com.amazic.ads.util.manager.banner.BannerBuilder
import com.amazic.ads.util.manager.banner.BannerManager
import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Base.BaseViewModel
import com.example.transparent_wallpaper.Screen.Language.LanguageActivity
import com.example.transparent_wallpaper.Utils.SystemUtils
import com.example.transparent_wallpaper.databinding.ActivitySplashBinding
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import java.util.Locale

class SplashActivity : BaseActivity<ActivitySplashBinding, BaseViewModel>() {
    private var adsSplashNew: AdsSplash? = null
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var bannerManager: BannerManager

    private lateinit var remoteConfig: FirebaseRemoteConfig

    override fun createBinding() = ActivitySplashBinding.inflate(layoutInflater)
    override fun setViewModel() = BaseViewModel()
    private fun startLanguageActivity() {
        startActivity(Intent(this@SplashActivity, LanguageActivity::class.java))
        finish()
    }

    private val adCallBack: AdCallback = object : AdCallback() {
        override fun onNextAction() {
            super.onNextAction()
            startLanguageActivity()
        }
    }
    private val interCallbackNew: InterCallback = object : InterCallback() {
        override fun onNextAction() {
            super.onNextAction()
            startLanguageActivity()
        }
    }

    override fun initView() {
        super.initView()
        setContentView(R.layout.activity_splash)

        // Khởi tạo Firebase Remote Config
        remoteConfig = FirebaseRemoteConfig.getInstance()
        // Cấu hình cài đặt Remote Config
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600) // Cài đặt thời gian giữa mỗi lần lấy dữ liệu
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)

        // Đặt các giá trị mặc định
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        // Lấy giá trị từ Firebase Remote Config
        fetchRemoteConfig()


        // Lưu ngôn ngữ của thiết bị
        val currentLanguage = Locale.getDefault().language
        Log.d("Language", currentLanguage)
        SystemUtils.saveDeviceLanguage(this, currentLanguage)
        // Vô hiệu hóa nút back
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })
        // Load banner quảng cáo
        val bannerBuilder = BannerBuilder(this, this)
            .initId(listOf(getString(R.string.banner_all)))
        bannerManager = BannerManager(bannerBuilder)
        bannerManager?.setReloadAds()

        handler.postDelayed({
            initShowAdsSplashNew()
        }, 5000)
    }

    private fun initShowAdsSplashNew() {
        Admob.getInstance().setTimeInterval(20000L)
        adsSplashNew = AdsSplash.init(
            true,
            true,
            "30_70"
        )
        var listOp = ArrayList<String>()
        listOp.add(getString(R.string.open_splash))
        var listInter = ArrayList<String>()
        listInter.add(getString(R.string.inter_splash))
        adsSplashNew?.showAdsSplash(
            this@SplashActivity,
            listOp,
            listInter,
            adCallBack,
            interCallbackNew
        )
    }


    private fun fetchRemoteConfig() {
        // Lấy dữ liệu từ Firebase Remote Config
        remoteConfig.fetchAndActivate().addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Lấy giá trị mã màu từ Firebase
                val colorValue = remoteConfig.getString("item_color")
                applyColorToItems(colorValue)
            } else {
                // Xử lý khi không lấy được dữ liệu
                applyColorToItems("#FFFFFF") // Áp dụng màu mặc định nếu không thành công
            }
        }
    }

    private fun applyColorToItems(colorString: String) {
        try {
            val color = Color.parseColor(colorString)
            // Ví dụ: Đổi màu TextView
            val textView = findViewById<TextView>(R.id.tv_name_app)
            textView.setTextColor(color)

            // Thay đổi màu các thành phần khác theo nhu cầu
            val layout = findViewById<View>(R.id.main)
            layout.setBackgroundColor(color)

        } catch (e: IllegalArgumentException) {
            // Xử lý lỗi nếu mã màu không hợp lệ
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}

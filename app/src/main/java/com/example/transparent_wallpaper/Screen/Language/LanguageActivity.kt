package com.example.transparent_wallpaper.Screen.Language

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amazic.ads.callback.NativeCallback
import com.amazic.ads.util.Admob
import com.amazic.ads.util.AdsConsentManager
import com.amazic.ads.util.manager.native_ad.NativeBuilder
import com.amazic.ads.util.manager.native_ad.NativeManager
import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Model.LanguageModel
import com.example.transparent_wallpaper.R
import com.example.transparent_wallpaper.Screen.Intro.Intro1Activity
import com.example.transparent_wallpaper.Utils.SystemUtils
import com.example.transparent_wallpaper.ViewModel.LanguageViewModel
import com.example.transparent_wallpaper.databinding.ActivityLanguageBinding
import com.example.transparent_wallpaper.view.tap
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import java.util.Locale

class LanguageActivity : BaseActivity<ActivityLanguageBinding,LanguageViewModel>() {
    override fun createBinding() = ActivityLanguageBinding.inflate(layoutInflater)
    override fun setViewModel() = LanguageViewModel()


    private lateinit var adapter: AdapterLanguage
    private var nativeManager: NativeManager? = null


    override fun initView() {
        super.initView()

        restoreLocale()
        viewModel = LanguageViewModel()


        adapter = AdapterLanguage(this, mutableListOf()) { language ->
            viewModel.setSelectedLanguage(this, language)
            updateSaveButtonVisibility(language)
        }
//        Admob.getInstance().loadNativeAd(this, getString(R.string.native_language), object : NativeCallback {
//            override fun onNativeAdLoaded(nativeAd: NativeAd) {
//                val adView = NativeAdView(this@LanguageActivity)
//                LayoutInflater.from(this@LanguageActivity)
//                    .inflate(R.layout.ads_native_large_language, adView, true)
//
//                // Find the FrameLayout for the native ad
//                val adContainer = findViewById<FrameLayout>(R.id.native_ads)
//                adContainer.removeAllViews()
//                adContainer.addView(adView)
//                Admob.getInstance().pushAdsToViewCustom(nativeAd, adView)
//            }
//
//            override fun onAdFailedToLoad() {
//                Log.d("Ad", "Failed to load native ad")
//            }
//        })




        val rcvLanguageStart = findViewById<RecyclerView>(R.id.rcvFrag)
        rcvLanguageStart.layoutManager = LinearLayoutManager(this)
        rcvLanguageStart.adapter = adapter


        // Mặc định ẩn nút imgbtnSaveLanguage khi khởi tạo
        binding.imgbtnSaveLanguage.visibility = View.GONE

        viewModel.languages.observe(this) { languages ->
            adapter.updateData(languages)
        }

        viewModel.selectedLanguage.observe(this) { selectedLanguage ->
            adapter.setSelectedLanguage(selectedLanguage)
            updateSaveButtonVisibility(selectedLanguage)
        }

        viewModel.languageStart(this)

        //val imgSave = findViewById<View>(R.id.imgbtnSaveLanguage)
        binding.imgbtnSaveLanguage.tap {
            val selectedLanguage = viewModel.selectedLanguage.value
            if (selectedLanguage != null) {
                viewModel.setLocale(this, selectedLanguage.code)
                SystemUtils.saveLocale(this, selectedLanguage.code)

                startActivity(Intent(this, Intro1Activity::class.java))
                finish()
            } else {
//                Toast.makeText(this, "Please select a language", Toast.LENGTH_SHORT).show()
            }
        }


        val list =  ArrayList<String>()
        list.add("ca-app-pub-3940256099942544/2247696110")
        loadNative(
            list,
            binding.nativeAds,
            R.layout.ads_native_shimer_large,
            R.layout.ads_native_language_start
        )



    }


    private fun restoreLocale() {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("selected_language", "en")
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun updateSaveButtonVisibility(selectedLanguage: LanguageModel?) {

        binding.imgbtnSaveLanguage.visibility = if (selectedLanguage != null) {
            View.VISIBLE
        } else {
            View.GONE
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
    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }


    override fun onStop() {
        super.onStop()
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }

}
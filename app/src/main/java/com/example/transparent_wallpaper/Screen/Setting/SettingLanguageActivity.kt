package com.example.transparent_wallpaper.Screen.Setting

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Model.LanguageModel
import com.example.transparent_wallpaper.R
import com.example.transparent_wallpaper.Screen.Home.HomeActivity
import com.example.transparent_wallpaper.Screen.Language.AdapterLanguage
import com.example.transparent_wallpaper.Utils.SystemUtils
import com.example.transparent_wallpaper.ViewModel.LanguageViewModel
import com.example.transparent_wallpaper.databinding.ActivitySettingLanguageBinding
import com.example.transparent_wallpaper.view.tap

class SettingLanguageActivity : BaseActivity<ActivitySettingLanguageBinding, LanguageViewModel>() {


    override fun createBinding() = ActivitySettingLanguageBinding.inflate(layoutInflater)

    override fun setViewModel() = LanguageViewModel()


    private lateinit var adapter: AdapterLanguage

    override fun initView() {
        super.initView()

        viewModel = LanguageViewModel()

        adapter = AdapterLanguage(this, mutableListOf()) { language ->
            viewModel.setSelectedLanguage(this, language)
        }

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )

        val rcvLanguageSetting = findViewById<RecyclerView>(R.id.rcvSettingFrag)
        rcvLanguageSetting.layoutManager = LinearLayoutManager(this)
        rcvLanguageSetting.adapter = adapter

        viewModel.languages.observe(this) { languages ->
            adapter.updateData(languages)
            loadSelectedLanguage()
        }


        viewModel.selectedLanguage.observe(this) { selectedLanguage ->
            adapter.setSelectedLanguage(selectedLanguage)
            updateSaveButtonVisibility(selectedLanguage)
        }

        viewModel.languageStart(this)

        binding.imgbtnback.setOnClickListener {
            navigateTo(HomeActivity::class.java)
        }



        binding.imgbtnsettingSaveLanguage.tap {
            val selectedLanguage = viewModel.selectedLanguage.value
            if (selectedLanguage != null) {
                viewModel.setLocale(this, selectedLanguage.code)
                //SystemUtils.saveLocale(this, selectedLanguage.code)
                saveLanguage(selectedLanguage.code)

                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Please select a language", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun loadSelectedLanguage() {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("selected_language", "en")
        languageCode?.let { code ->
            viewModel.languages.value?.find { it.code == code }?.let { language ->
                viewModel.setSelectedLanguage(this, language)
            }
        }
    }

    private fun saveLanguage(languageCode: String) {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("selected_language", languageCode)
        editor.apply()
    }


    private fun updateSaveButtonVisibility(selectedLanguage: LanguageModel?) {
        binding.imgbtnsettingSaveLanguage.visibility = if (selectedLanguage != null) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun navigateTo(targetClass: Class<*>) {
        val intent = Intent(this, targetClass)
        startActivity(intent)
    }

}
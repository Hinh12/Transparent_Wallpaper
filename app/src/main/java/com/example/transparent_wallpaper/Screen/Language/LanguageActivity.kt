package com.example.transparent_wallpaper.Screen.Language

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Model.LanguageModel
import com.example.transparent_wallpaper.R
import com.example.transparent_wallpaper.Screen.Intro.Intro1Activity
import com.example.transparent_wallpaper.Utils.SystemUtils
import com.example.transparent_wallpaper.ViewModel.LanguageViewModel
import com.example.transparent_wallpaper.databinding.ActivityLanguageBinding
import com.example.transparent_wallpaper.view.tap
import java.util.Locale

class LanguageActivity : BaseActivity<ActivityLanguageBinding,LanguageViewModel>() {
    override fun createBinding() = ActivityLanguageBinding.inflate(layoutInflater)
    override fun setViewModel() = LanguageViewModel()


    private lateinit var adapter: AdapterLanguage


    override fun initView() {
        super.initView()
        setContentView(R.layout.activity_language)
        restoreLocale()
        viewModel = LanguageViewModel()


        adapter = AdapterLanguage(this, mutableListOf()) { language ->
            viewModel.setSelectedLanguage(this, language)
        }

        val rcvLanguageStart = findViewById<RecyclerView>(R.id.rcvFrag)
        rcvLanguageStart.layoutManager = LinearLayoutManager(this)
        rcvLanguageStart.adapter = adapter


        viewModel.languages.observe(this) { languages ->
            adapter.updateData(languages)
        }

        viewModel.selectedLanguage.observe(this) { selectedLanguage ->
            adapter.setSelectedLanguage(selectedLanguage)
            updateSaveButtonVisibility(selectedLanguage)
        }

        viewModel.languageStart(this)

        val imgSave = findViewById<View>(R.id.imgbtnSaveLanguage)
        imgSave.tap {
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

    private fun saveLanguage(languageCode: String) {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("selected_language", languageCode)
        editor.apply()
    }

}
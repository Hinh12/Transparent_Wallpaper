package com.example.transparent_wallpaper.ViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.transparent_wallpaper.Base.BaseViewModel
import com.example.transparent_wallpaper.Model.LanguageModel
import com.example.transparent_wallpaper.R
import com.example.transparent_wallpaper.Utils.SystemUtils
import java.util.Locale

class LanguageViewModel : BaseViewModel() {

    val languages = MutableLiveData<List<LanguageModel>>()
    val selectedLanguage = MutableLiveData<LanguageModel>()

    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    // Mã ngôn ngữ của thiết bị (Device language code)
    private val _langDevice = MutableLiveData<String>()
    val langDevice: LiveData<String>
        get() = _langDevice

    // Mã ngôn ngữ đã chọn (Selected language code)
    private val _codeLang = MutableLiveData<String>()
    val codeLang: LiveData<String>
        get() = _codeLang


        fun languageStart(context: Context) {
         val deviceLanguageCode = SystemUtils.getDeviceLanguage(context)
        Log.d("LanguageViewModel", deviceLanguageCode.toString())
         val listLanguage = mutableListOf(
            LanguageModel("English", "en", false, R.drawable.img_frag_eng),
            LanguageModel("Hindi", "hi", false, R.drawable.img_frag_india),
            LanguageModel("Spanish", "es", false, R.drawable.img_frag_spanish),
            LanguageModel("French", "fr", false, R.drawable.img_frag_french),
            LanguageModel("German", "de", false, R.drawable.img_frag_germany),
            LanguageModel("Indonesian", "in", false, R.drawable.img_frag_indo),
            LanguageModel("Portuguese", "pt", false, R.drawable.img_frag_portu),
            LanguageModel("China", "zh", false, R.drawable.img_frag_china),
        )

        Log.d("bbb", deviceLanguageCode.toString())

        val deviceLanguageIndex = listLanguage.indexOfFirst { it.code == deviceLanguageCode }
        Log.d("language in the list", deviceLanguageCode.toString())

        if (deviceLanguageIndex != -1) {
            val deviceLanguage = listLanguage.removeAt(deviceLanguageIndex)
            listLanguage.add(0, deviceLanguage)
        } else {
            val englishLanguageIndex = listLanguage.indexOfFirst { it.code == "en" }
            if (englishLanguageIndex != -1) {
                val englishLanguage = listLanguage.removeAt(englishLanguageIndex)
                listLanguage.add(0, englishLanguage)
            }
        }
        languages.postValue(listLanguage)
    }

    fun languageSetting(context: Context) {

        var langDevice = "en"
        var codeLang = "en"
        val listLanguage = mutableListOf<LanguageModel>()
        // Lấy mã ngôn ngữ đã chọn trước đó từ SharedPreferences

        // Khởi tạo danh sách ngôn ngữ (Initialize the list of languages)
        listLanguage.add(LanguageModel("English", "en", false, R.drawable.img_frag_eng))
        listLanguage.add(LanguageModel("Hindi", "hi", false, R.drawable.img_frag_india))
        listLanguage.add(LanguageModel("Spanish", "es", false, R.drawable.img_frag_spanish))
        listLanguage.add(LanguageModel("French", "fr", false, R.drawable.img_frag_french))
        listLanguage.add(LanguageModel("German", "de", false, R.drawable.img_frag_germany))
        listLanguage.add(LanguageModel("Indonesian", "in", false, R.drawable.img_frag_indo))
        listLanguage.add(LanguageModel("Portuguese", "pt", false, R.drawable.img_frag_portu))
        listLanguage.add(LanguageModel("China", "zh", false, R.drawable.img_frag_china))

        val preferredLanguage = SystemUtils.getPreLanguage(context)
        val selectedLanguageIndex = listLanguage.indexOfFirst { it.code == preferredLanguage }
        if (selectedLanguageIndex != -1) {
            val selectedLanguage = listLanguage.removeAt(selectedLanguageIndex).copy(active = true)
            listLanguage.add(0, selectedLanguage)
        }
        langDevice = preferredLanguage.toString()
        codeLang = preferredLanguage.toString()

        _langDevice.postValue(langDevice)
        _codeLang.postValue(codeLang)
        languages.postValue(listLanguage)
    }
    // Lưu ngôn ngữ đã chọn và cập nhật vào LiveData (Save the selected language and update LiveData)
    fun setSelectedLanguage(context: Context, language: LanguageModel) {
        selectedLanguage.value = language
        _codeLang.postValue(language.code)
        saveSelectedLanguage(context, language.code)
    }
    // Lưu mã ngôn ngữ vào SharedPreferences (Store the language code in SharedPreferences)
    private fun saveSelectedLanguage(context: Context, languageCode: String) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("selected_language", languageCode).apply()
    }
}
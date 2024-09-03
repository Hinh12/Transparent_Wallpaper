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

class LanguageViewModel: BaseViewModel()  {

    val languages = MutableLiveData<List<LanguageModel>>()
    val selectedLanguage = MutableLiveData<LanguageModel>()

    // Phương thức thay đổi ngôn ngữ của ứng dụng (Method to change the app's language)
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


    // Khởi tạo danh sách ngôn ngữ và đặt ngôn ngữ của thiết bị lên đầu (Initialize the list of languages and set the device's language to the top)
    fun languageStart(context: Context) {
        // Lấy mã ngôn ngữ của thiết bị (Get device language code)
        val deviceLanguageCode = SystemUtils.getDeviceLanguage(context)
        Log.d("bbb", deviceLanguageCode.toString())

        // Tạo danh sách các ngôn ngữ hỗ trợ (Create a list of supported languages)
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

        // Tìm ngôn ngữ của thiết bị trong danh sách (Find the device language in the list)
        val deviceLanguageIndex = listLanguage.indexOfFirst { it.code == deviceLanguageCode }
        Log.d("aaa", deviceLanguageCode.toString())

        // Nếu tìm thấy ngôn ngữ của thiết bị, đặt nó lên đầu (If found, set it to the top)
        if (deviceLanguageIndex != -1) {
            val deviceLanguage = listLanguage.removeAt(deviceLanguageIndex)
            listLanguage.add(0, deviceLanguage)
        } else {
            // Nếu không tìm thấy, đặt tiếng Anh lên đầu (If not found, set English to the top)
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

        // Khởi tạo danh sách ngôn ngữ (Initialize the list of languages)
        listLanguage.add(LanguageModel("English", "en", false, R.drawable.img_frag_eng))
        listLanguage.add(LanguageModel("Hindi", "hi", false, R.drawable.img_frag_india))
        listLanguage.add(LanguageModel("Spanish", "es", false, R.drawable.img_frag_spanish))
        listLanguage.add(LanguageModel("French", "fr", false, R.drawable.img_frag_french))
        listLanguage.add(LanguageModel("German", "de", false, R.drawable.img_frag_germany))
        listLanguage.add(LanguageModel("Indonesian", "in", false, R.drawable.img_frag_indo))
        listLanguage.add(LanguageModel("Portuguese", "pt", false, R.drawable.img_frag_portu))
        listLanguage.add(LanguageModel("China", "zh", false, R.drawable.img_frag_china))



        // Lấy ngôn ngữ đã chọn trước đó của người dùng (Retrieve user's previously selected language)
        val preferredLanguage = SystemUtils.getPreLanguage(context)
        val selectedLanguageIndex = listLanguage.indexOfFirst { it.code == preferredLanguage }

        // Đặt ngôn ngữ đã chọn lên đầu danh sách và đánh dấu là active (Move the selected language to the top and mark as active)
        if (selectedLanguageIndex != -1) {
            val selectedLanguage = listLanguage.removeAt(selectedLanguageIndex).copy(active = true)
            listLanguage.add(0, selectedLanguage)
        }

        // Cập nhật mã ngôn ngữ của thiết bị và ngôn ngữ đã chọn (Update device language code and selected language code)
        langDevice = preferredLanguage.toString()
        codeLang = preferredLanguage.toString()
        listLanguage.forEachIndexed { index, language ->
            if (index != 0) {
                listLanguage[index] = language.copy(active = false)
            }
        }

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
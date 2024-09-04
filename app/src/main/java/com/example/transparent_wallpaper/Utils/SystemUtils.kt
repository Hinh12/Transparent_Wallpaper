package com.example.transparent_wallpaper.Utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object SystemUtils {
    private var myLocale: Locale? = null
    private const val PREF_NAME = "data"
    private const val KEY_DEVICE_LANGUAGE = "device_language"
    fun saveLocale(context: Context, lang: String?) {
        setPreLanguage(context, lang)
    }

    fun setLocale(context: Context) {
        val language = getPreLanguage(context)
        if (language == "") {
            val config = Configuration()
            val locale = Locale.getDefault()
            Locale.setDefault(locale)
            config.locale = locale
            context.resources
                .updateConfiguration(config, context.resources.displayMetrics)
        } else {
            changeLang(language, context)
        }
    }

    fun changeLang(lang: String?, context: Context) {
        if (lang.equals("", ignoreCase = true)) return
        myLocale = Locale(lang)
        saveLocale(context, lang)
        Locale.setDefault(myLocale)
        val config = Configuration()
        config.locale = myLocale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
    fun getPreLanguage(mContext: Context): String? {
        val preferences = mContext.getSharedPreferences("data", Context.MODE_PRIVATE)
        return preferences.getString("KEY_LANGUAGE", "")
    }

    fun setPreLanguage(context: Context, language: String?) {
        if (language == null || language == "") {
        } else {
            val preferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("KEY_LANGUAGE", language)
            editor.apply()
        }
    }



    // Lưu ngôn ngữ gốc của thiết bị
    fun saveDeviceLanguage(context: Context, languageCode: String) {
        val pre = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = pre.edit()
        editor.putString(KEY_DEVICE_LANGUAGE, languageCode)
        editor.commit()
    }


    fun getDeviceLanguage(context: Context): String? {
        val pre = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pre.getString(KEY_DEVICE_LANGUAGE, null)
    }
}
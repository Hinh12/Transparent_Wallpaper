package com.example.transparent_wallpaper.Utils

import android.content.Context

class SharePrefRemote {

    var open_splash: String = "open_splash"

    var inter_splash: String = "inter_splash"

    var Collapsible_banner: String = "Collapsible_banner"

    var rewarded_vip: String = "rewarded_vip"
    var appopen_resume: String = "appopen_resume"
    var native_language: String = "nav_language"
    var native_intro: String = "native_intro"
    var native_permission: String = "native_permission"
    var native_view: String = "native_view"
    var native_done: String = "native_done"
    var inter_category: String = "inter_category"
    var inter_done: String = "inter_done"
    var inter_my_wallpaper: String = "inter_my_wallpaper"

    var inter_intro: String = "inter_intro"
    var inter_preview: String = "inter_preview"

    //   public static String banner_all = "banner_all";
    var banner_all: String = "collapse_all"

    var interval_between_interstitial: String = "interval_between_interstitial"
    var interval_interstitial_from_start: String = "interval_interstitial_from_start"

    var rate_aoa_inter_splash: String = "30_70"
    var native_live: String = "native_live"
    var native_top: String = "native_top"

    var interval_between_inter_and_collap: String = "interval_between_inter_and_collap"
    var reload_collap: String = "reload_collap"
    var interstitial_from_start: String = "interstitial_from_start"

    var rating_popup: String = "rating_popup"

//    fun get_config(context: Context, name_config: String): Boolean {
//        val pre = context.getSharedPreferences("remote_fill", Context.MODE_PRIVATE)
//        return if (name_config == "style_screen") pre.getBoolean(name_config, false)
//        else pre.getBoolean(name_config, true) && AdsConsentManager.getConsentResult(context)
//    }

    fun set_config(context: Context, name_config: String?, config: Boolean) {
        val pre = context.getSharedPreferences("remote_fill", Context.MODE_PRIVATE)
        val editor = pre.edit()
        editor.putBoolean(name_config, config)
        editor.apply()
    }

    fun set_config(context: Context, name_config: String?, config: Long) {
        val pre = context.getSharedPreferences("remote_fill", Context.MODE_PRIVATE)
        val editor = pre.edit()
        editor.putLong(name_config, config)
        editor.apply()
    }

    fun get_config_long(context: Context, name_config: String?): Long {
        val pre = context.getSharedPreferences("remote_fill", Context.MODE_PRIVATE)
        return pre.getLong(name_config, 0) * 1000
    }

    fun get_config_string(context: Context, name_config: String?): String? {
        val pre = context.getSharedPreferences("remote_fill", Context.MODE_PRIVATE)
        return pre.getString(name_config, "")
    }

    fun set_config_string(context: Context, name_config: String?, config: String?) {
        val pre = context.getSharedPreferences("remote_fill", Context.MODE_PRIVATE)
        val editor = pre.edit()
        editor.putString(name_config, config)
        editor.apply()
    }

    fun set_config_long(context: Context, name_config: String?, config: Long) {
        val pre = context.getSharedPreferences("remote_fill", Context.MODE_PRIVATE)
        val editor = pre.edit()
        editor.putLong(name_config, config)
        editor.apply()
    }
}
package com.example.transparent_wallpaper.Model

import android.content.SharedPreferences


object Rate {
    private const val RATED_KEY = "rated"
    private var sharedPreferences: SharedPreferences? = null

    // Initialize the SharedPreferences instance
    fun initialize(sharedPreferences: SharedPreferences) {
        Rate.sharedPreferences = sharedPreferences
    }

    // Check if the item has been rated
    fun isRated(): Boolean {
        return sharedPreferences?.getBoolean(RATED_KEY, false) ?: false
    }

    // Set the rating status
    fun setRated(isRated: Boolean) {
        sharedPreferences?.edit()?.putBoolean(RATED_KEY, isRated)?.apply()
    }
}

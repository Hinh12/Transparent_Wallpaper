package com.example.transparent_wallpaper

import android.os.Bundle
import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Base.BaseViewModel
import com.example.transparent_wallpaper.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics

class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {
    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun setViewModel() = BaseViewModel()
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun initView() {
        super.initView()
         // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = Firebase.analytics

        val bundle = Bundle().apply {
            putString("state_noti", "ON")
        }
        firebaseAnalytics.logEvent("notification_onoff", bundle)
    }


}
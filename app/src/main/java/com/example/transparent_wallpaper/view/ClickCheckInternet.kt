package com.example.transparent_wallpaper.view

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.view.View
import com.example.transparent_wallpaper.SplashActivity
import com.example.transparent_wallpaper.Utils.CheckInternet

fun View.tap(action: (view: View?) -> Unit) {
    setOnClickListener(object : TapListener() {
        override fun onTap(v: View?) {
            if (!CheckInternet.haveNetworkConnection(context)) {
                context.findActivity()?.let {
                    val intent = Intent(it, SplashActivity::class.java)
                    it.startActivity(intent)
                    it.overridePendingTransition(0, 0)
                }
                return
            }
            action(v)
        }
    })
}

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
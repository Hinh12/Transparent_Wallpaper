package com.example.transparent_wallpaper.Screen.HDWallpaper

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.transparent_wallpaper.R
import com.example.transparent_wallpaper.Screen.Home.HomeActivity

class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_success)
        val btnDone = findViewById<Button>(R.id.btn_done);

        btnDone.setOnClickListener {
            startActivity(
                Intent(
                    this@SuccessActivity,
                    HomeActivity::class.java
                )
            )
        }
    }
}
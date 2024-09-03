package com.example.transparent_wallpaper.Screen.Intro

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.transparent_wallpaper.Base.BaseActivity
import com.example.transparent_wallpaper.Base.BaseViewModel
import com.example.transparent_wallpaper.MainActivity
import com.example.transparent_wallpaper.Model.Intro
import com.example.transparent_wallpaper.R
import com.example.transparent_wallpaper.Screen.Home.HomeActivity
import com.example.transparent_wallpaper.Screen.Language.LanguageActivity
import com.example.transparent_wallpaper.databinding.ActivityIntro1Binding
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class Intro1Activity : BaseActivity<ActivityIntro1Binding, BaseViewModel>() {


    private lateinit var listItemSlide: List<Intro>
    override fun createBinding() = ActivityIntro1Binding.inflate(layoutInflater)

    override fun setViewModel() = BaseViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_intro1)

        listItemSlide = arrayListOf(
            Intro(R.drawable.img_intro1, R.string.transparency, R.string.reveal_your_screens_beauty),
            Intro(R.drawable.img_intro2, R.string.crystal, R.string.clarity_and_style),
            Intro(R.drawable.img_intro03, R.string.clearvision, R.string.elevate_your_screens_allure)
        )

        val viewPagerIntro: ViewPager2 = findViewById(R.id.view_paper2)
        val dotsIndicator: DotsIndicator = findViewById(R.id.dots_indicator)


        viewPagerIntro.adapter = AdapterSlideIntro(listItemSlide, viewPagerIntro, this)
        dotsIndicator.attachTo(viewPagerIntro)



        findViewById<View>(R.id.txt_intro_Next).setOnClickListener {
            val currentItem = viewPagerIntro.currentItem
            if (currentItem < listItemSlide.size - 1) {
                viewPagerIntro.currentItem = currentItem + 1
            } else {
                navigateTo(HomeActivity::class.java)
            }
        }

    }

    private fun navigateTo(destination: Class<*>) {
        startActivity(Intent(this, destination))
        finish()
    }
}

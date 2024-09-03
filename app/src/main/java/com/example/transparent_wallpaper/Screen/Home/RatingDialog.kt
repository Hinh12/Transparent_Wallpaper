package com.example.transparent_wallpaper.Screen.Home

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import com.example.transparent_wallpaper.R
import com.example.transparent_wallpaper.databinding.DialogRateBinding

class RatingDialog @SuppressLint("ResourceType") constructor(context: Context?) :
    Dialog(context!!, R.style.Base_Theme_Transparent_Wallpaper) {
    private var onPress: OnPress? = null
    private var s = 5
    private val binding: DialogRateBinding = DialogRateBinding.inflate(layoutInflater)

    init {
        setContentView(binding.getRoot())
        val attributes = window!!.attributes
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = attributes
        window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        binding.ratingBar.setRating(5.0f)

        onclick()
        changeRating()
    }

    interface OnPress {
        fun send(s: Int)
        fun rating(s: Int)
        fun cancel()
        fun later()
        fun gotIt()
    }

    fun init(onPress: OnPress?) {
        this.onPress = onPress
    }

    private fun changeRating() {
        binding.ratingBar.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            s = rating.toInt()
        })
    }

    private fun onclick() {
        binding.btnRate.setOnClickListener { v ->
            if (binding.ratingBar.getRating() === 0f) {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.Rate),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (binding.ratingBar.getRating() <= 3.0) {
                if (onPress != null) {
                    onPress!!.send(s)
                }
            } else {
                if (onPress != null) {
                    onPress!!.rating(s)
                }
            }
        }

        binding.imgClose.setOnClickListener { v ->
            if (onPress != null) {
                onPress!!.cancel()
            }
        }
    }
}

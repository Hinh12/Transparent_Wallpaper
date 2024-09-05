package com.example.transparent_wallpaper.ViewModel

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.transparent_wallpaper.R
import android.animation.ObjectAnimator
import android.view.animation.LinearInterpolator
class CustomDotsIndicator(
    private val dotsLayout: LinearLayout,
    count: Int,
    private val context: Context
) {
    private val dots = mutableListOf<ImageView>()

    init {
        initializeDots(count)
    }

    private fun initializeDots(count: Int) {
        dotsLayout.removeAllViews()
        for (i in 0 until count) {
            val dot = ImageView(context)
            dot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dot_unselected))
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(8, 0, 8, 0)
            }
            dotsLayout.addView(dot, params)
            dots.add(dot)
        }
    }

    fun updateDots(position: Int) {
        val visibleDots = minOf(dots.size, 3)

        for (i in dots.indices) {
            dots[i].visibility = if (i in position - 1..position + 1) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        when {
            position == 0 -> {
                dots[1].visibility = View.VISIBLE
                dots[2].visibility = View.GONE
            }
            position == dots.size - 1 -> {
                dots[dots.size - 2].visibility = View.VISIBLE
                dots[dots.size - 3].visibility = View.GONE
            }
            else -> {
                dots[position - 1].visibility = View.VISIBLE
                dots[position + 1].visibility = View.VISIBLE
            }
        }

        for (i in dots.indices) {
            val dot = dots[i]
            dot.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    if (i == position) R.drawable.dot_selected else R.drawable.dot_unselected
                )
            )

            // Thêm hiệu ứng chuyển động cho dot
            val scaleXAnimator = ObjectAnimator.ofFloat(dot, "scaleX", if (i == position) 1f else 1f)
            val scaleYAnimator = ObjectAnimator.ofFloat(dot, "scaleY", if (i == position) 1f else 1f)
            val alphaAnimator = ObjectAnimator.ofFloat(dot, "alpha", if (i == position) 1f else 0.5f)

            scaleXAnimator.duration = 300
            scaleYAnimator.duration = 300
            alphaAnimator.duration = 300

            scaleXAnimator.interpolator = LinearInterpolator()
            scaleYAnimator.interpolator = LinearInterpolator()
            alphaAnimator.interpolator = LinearInterpolator()

            scaleXAnimator.start()
            scaleYAnimator.start()
            alphaAnimator.start()
        }
    }
}
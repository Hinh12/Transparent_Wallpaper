package com.example.transparent_wallpaper.Screen.SetWallpaper
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.example.transparent_wallpaper.R

class CustomDotsIndicator1(
    private val container: LinearLayout,
    private val totalDots: Int,
    context: Context
) {
    private val activeColor = Color.WHITE
    private val inactiveColor = Color.GRAY

    private val dotSize = 16 // Kích thước dot (dp)
    private val dotMargin = 8 // Khoảng cách giữa các dot (dp)

    private val dots = mutableListOf<View>()

    init {
        for (i in 0 until totalDots) {
            val dot = createDotView(context)
            dots.add(dot)
            container.addView(dot)
        }
        updateDots(0) // Đặt dot đầu tiên là active
    }

    private fun createDotView(context: Context): View {
        val dot = View(context).apply {
            val layoutParams = LinearLayout.LayoutParams(
                dpToPx(dotSize),
                dpToPx(dotSize)
            ).apply {
                setMargins(dpToPx(dotMargin), 0, dpToPx(dotMargin), 0)
            }
            this.layoutParams = layoutParams
            setBackgroundColor(inactiveColor)
            alpha = 0.5f // Độ trong suốt ban đầu
            // Tạo hình tròn cho dot
            background = context.getDrawable(R.drawable.dot_backgroud)
        }
        return dot
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * container.context.resources.displayMetrics.density).toInt()
    }

    fun updateDots(selectedPosition: Int) {
        for (i in dots.indices) {
            val dot = dots[i]
            if (i == selectedPosition) {
                dot.setBackgroundColor(activeColor)
                dot.animate().scaleX(1.5f).scaleY(1.5f).alpha(1f).duration = 300 // Hiệu ứng khi active
            } else {
                dot.setBackgroundColor(inactiveColor)
                dot.animate().scaleX(1f).scaleY(1f).alpha(0.5f).duration = 300 // Hiệu ứng khi inactive
            }
        }
    }
}

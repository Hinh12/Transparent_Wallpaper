package com.example.transparent_wallpaper.Base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.transparent_wallpaper.Utils.SharePrefRemote


// Abstract class for activities that use ViewBinding and ViewModel
abstract class BaseActivityViewModel<VB : ViewBinding, V : ViewModel> : AppCompatActivity() {

    protected lateinit var binding: VB
    protected lateinit var viewModel: V

    // Default value for interstitial ad interval
    private val INTERVAL_INTERSTITIAL_FROM_START = 15 * 1000L // 15 seconds

    // Abstract methods to be implemented by subclasses
    protected abstract fun createBinding(): VB
    protected abstract fun setViewModel(): V

    protected open fun initView() {}
    protected open fun bindView() {}
    protected abstract fun viewModel()
    protected open fun initData() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = createBinding()
        setContentView(binding.root)
        viewModel = setViewModel()

        // Initialize any necessary components
        initView()
        viewModel()
        hideNavigationBar()
    }

    // Hide the navigation bar for immersive full-screen experience
    protected fun hideNavigationBar() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
    }

    override fun onResume() {
        super.onResume()
        // Perform actions needed when resuming the activity
        handleActivityResume()
    }

    private fun handleActivityResume() {
        // This method could be used to perform any actions required when the activity resumes
    }

    // Navigate to another activity
    protected fun navigateTo(activityClass: Class<*>) {
        startActivity(Intent(this, activityClass))
        finish()
    }

    // Navigate to another activity with a position parameter
    protected fun navigateToAddPosition(activityClass: Class<*>, pos: Int) {
        val intent = Intent(this, activityClass).apply {
            putExtra("position", pos)
        }
        startActivity(intent)
        finish()
    }
}
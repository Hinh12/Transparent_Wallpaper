package com.example.transparent_wallpaper.Screen.SetWallpaper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.transparent_wallpaper.Model.HdWallpaperModel
import com.example.transparent_wallpaper.R
import com.example.transparent_wallpaper.Screen.HDWallpaper.HDWallpaperAdapter
import com.example.transparent_wallpaper.databinding.ItemRcvSetwallpaperBinding

class SetWallViewPager2Adapter(
    private val list: List<HdWallpaperModel>,
    private val viewPager2: ViewPager2
) : RecyclerView.Adapter<SetWallViewPager2Adapter.ViewPager2ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPager2ViewHolder {
        val binding = ItemRcvSetwallpaperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPager2ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPager2ViewHolder, position: Int) {
        holder.binding.imageslide.setImageResource(list[position].imageUrl)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewPager2ViewHolder(val binding: ItemRcvSetwallpaperBinding) : RecyclerView.ViewHolder(binding.root)
}
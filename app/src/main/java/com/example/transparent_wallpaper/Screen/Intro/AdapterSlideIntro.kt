package com.example.transparent_wallpaper.Screen.Intro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.transparent_wallpaper.Model.Intro
import com.example.transparent_wallpaper.R


class AdapterSlideIntro(
    private val listSlideItems: List<Intro>,
    private val viewPager2: ViewPager2,
    private val context: Context
) : RecyclerView.Adapter<AdapterSlideIntro.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_page, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val slideItem = listSlideItems[position]
        if (slideItem != null) {
            holder.img.setImageResource(slideItem.img_intro)
            holder.text1.text = context.getString(slideItem.txt_intro_01)
            holder.text2.text = context.getString(slideItem.txt_intro_02)
        }
    }

    override fun getItemCount(): Int {
        return listSlideItems.size
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.img_intro_01)
        val text1: TextView = itemView.findViewById(R.id.txt_intro_011)
        val text2: TextView = itemView.findViewById(R.id.txt_intro_012)
    }
}
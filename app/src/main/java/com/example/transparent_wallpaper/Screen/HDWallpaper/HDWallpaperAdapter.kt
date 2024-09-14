package com.example.transparent_wallpaper.Screen.HDWallpaper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.transparent_wallpaper.Model.HdWallpaperModel
import com.example.transparent_wallpaper.R

class HDWallpaperAdapter(
    private val context: Context,
    private val list: List<HdWallpaperModel>,
    private val mListener: OnclickItem // Thêm mListener trực tiếp
) : RecyclerView.Adapter<HDWallpaperAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgView: ImageView = itemView.findViewById(R.id.img_hdwallpaper)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rcv_hdwallpaper, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.imgView.setImageResource(item.imageUrl)
        holder.itemView.setOnClickListener {
            mListener.Onclick(holder.adapterPosition)
        }

    }
}
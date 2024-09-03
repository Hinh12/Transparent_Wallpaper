package com.example.transparent_wallpaper.Screen.Intro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.transparent_wallpaper.Model.Intro
import com.example.transparent_wallpaper.R

// Khai báo và truyền danh sách `list` qua constructor
class ViewPageAdapter(private val list: List<Intro>) : RecyclerView.Adapter<ViewPageAdapter.ViewHolder>() {

    // ViewHolder class để giữ các view của layout item_page
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_intro_01)
        val textView1: TextView = itemView.findViewById(R.id.txt_intro_011)
        val textView2: TextView = itemView.findViewById(R.id.txt_intro_012)
    }

    // Tạo ViewHolder mới từ layout item_page.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false)
        return ViewHolder(itemView)
    }

    // Trả về số lượng mục trong danh sách
    override fun getItemCount(): Int {
        return list.size
    }

    // Gán dữ liệu từ đối tượng Intro vào các view trong ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val introItem = list[position]

        // Sử dụng Glide để tải ảnh từ URL hoặc tài nguyên drawable
        Glide.with(holder.itemView.context)
            .load(introItem.img_intro) // URL hoặc tài nguyên ảnh
            .placeholder(R.drawable.img_cloud_download) // Hình ảnh hiển thị trong lúc tải
            .error(R.drawable.img_error) // Hình ảnh hiển thị khi có lỗi tải ảnh
            .into(holder.imageView)

        // Gán văn bản vào các TextView
        holder.textView1.text = introItem.txt_intro_01.toString()
        holder.textView2.text = introItem.txt_intro_02.toString()
    }
}

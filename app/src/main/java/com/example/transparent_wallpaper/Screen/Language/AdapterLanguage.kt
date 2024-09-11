package com.example.transparent_wallpaper.Screen.Language

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amazic.ads.util.AppOpenManager
import com.amazic.ads.util.manager.native_ad.NativeManager
import com.example.transparent_wallpaper.Model.LanguageModel
import com.example.transparent_wallpaper.R
import com.example.transparent_wallpaper.view.tap

class AdapterLanguage(
    private val context: Context,
    private val list: MutableList<LanguageModel>,
    private val listener: (LanguageModel) -> Unit
) : RecyclerView.Adapter<AdapterLanguage.ViewHolder>() {

    private var selectedPosition = list.indexOfFirst { it.active }
    private val lastAdTime: Long = 0
    private val AD_INTERVAL = (20 * 1000 // 20 giây
            ).toLong()
    private val native_ads: FrameLayout? = null
    var nativeManager: NativeManager? = null
    private val appOpenManager: AppOpenManager? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imgFrag)
        val txtCountry: TextView = itemView.findViewById(R.id.txtCountry)
        val imgButtton: ImageView = itemView.findViewById(R.id.imgButton)

        fun bind(item: LanguageModel, isSelected: Boolean) {
            imageView.setImageResource(item.image)
            txtCountry.text = item.languageName
            //imgButtton.isChecked = isSelected

            if (isSelected) {
                itemView.setBackgroundResource(R.drawable.custom_item_frag_selected)
                imgButtton.setImageResource(R.drawable.custom_radio_selected)
            } else {
                itemView.setBackgroundResource(R.drawable.custom_item_frag_unselected)
                imgButtton.setImageResource(R.drawable.custom_radio_unselected)
            }


            itemView.tap {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition

                if (previousPosition != -1 && previousPosition != selectedPosition) {
                    list[previousPosition].active = false
                    notifyItemChanged(previousPosition)
                }

                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)

                listener(item)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_languagescreen, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val isSelected = position == selectedPosition || item.active
        holder.bind(item, isSelected)
    }

    fun updateData(newList: List<LanguageModel>) {
        list.clear()
        list.addAll(newList)
        selectedPosition = list.indexOfFirst { it.active }
        notifyDataSetChanged()
    }

    fun setSelectedLanguage(selectedLanguage: LanguageModel) {
        val previousPosition = selectedPosition
        selectedPosition = list.indexOfFirst { it == selectedLanguage }
        if (previousPosition != -1) {
            notifyItemChanged(previousPosition)
        }
        if (selectedPosition != -1) {
            notifyItemChanged(selectedPosition)
        }
    }


}
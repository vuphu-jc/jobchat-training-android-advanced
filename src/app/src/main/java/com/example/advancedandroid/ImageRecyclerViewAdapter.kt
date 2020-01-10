package com.example.advancedandroid

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ImageRecyclerViewAdapter(private val context: Context, private val data: MutableList<String>)
    : RecyclerView.Adapter<ImageRecyclerViewAdapter.ViewHolder>() {

    private var spanCount: Int = 1
    private var displayMetrics = Utils.getDisplayMetrics(context as Activity)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imgImage)
    }

    fun addData(data: MutableList<String>) {
        this.data.addAll(data)
        notifyItemRangeChanged(this.data.size - data.size, data.size)
    }

    fun setSpanCount(spanCount: Int) {
        this.spanCount = spanCount
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val size = displayMetrics.widthPixels / spanCount
        val params = LinearLayout.LayoutParams(size, size)
        holder.imageView.layoutParams = params
        Glide.with(context).load(data[position])
            .into(holder.imageView);
    }
}
package com.example.advancedandroid

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ImageRecyclerViewAdapter(private val context: Context, private val data: MutableList<String>)
    : RecyclerView.Adapter<ImageRecyclerViewAdapter.ViewHolder>() {

    private var mSpanCount: Int = 1
    private var mDisplayMetrics = Utils.getDisplayMetrics(context as Activity)

    fun addData(data: List<String>) {
        this.data.addAll(data)
        notifyItemRangeChanged(this.data.size - data.size, data.size)
    }

    fun setSpanCount(spanCount: Int) {
        this.mSpanCount = spanCount
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
        holder.bind(data[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imgImage)

        fun bind(uri: String) {
            val size = mDisplayMetrics.widthPixels / mSpanCount
            val params = LinearLayout.LayoutParams(size, size)
            imageView.layoutParams = params
            imageView.setImageBitmap(null)
            Utils.ImageLoader.from(context).load(data[position], imageView)
        }
    }
}
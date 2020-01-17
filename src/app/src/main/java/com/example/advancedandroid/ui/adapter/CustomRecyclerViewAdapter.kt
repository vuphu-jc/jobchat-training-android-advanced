package com.example.advancedandroid.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advancedandroid.model.SongURL

abstract class CustomDataRecyclerView(val data: Any?) {
    abstract fun createViewHolder(context: Context, parent: ViewGroup): CustomViewHolderRecyclerView
}

abstract class CustomViewHolderRecyclerView(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun bind(customData: Any?)
}

open class CustomRecyclerViewAdapter(private val context: Context, private val data: List<CustomDataRecyclerView>)
    : RecyclerView.Adapter<CustomViewHolderRecyclerView>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolderRecyclerView {
        return data[viewType].createViewHolder(context, parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: CustomViewHolderRecyclerView, position: Int) {
        holder.bind(data[position].data)
    }
}
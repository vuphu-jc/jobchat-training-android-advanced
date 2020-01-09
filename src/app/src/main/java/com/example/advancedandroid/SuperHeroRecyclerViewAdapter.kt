package com.example.advancedandroid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class SuperHeroRecyclerViewAdapter
    (private val data: MutableList<SuperHero>, private val context: Context, private val type: Int):
            RecyclerView.Adapter<SuperHeroRecyclerViewAdapter.ViewHolder>() {

    companion object LayoutManagerType {
        const val LINEAR: Int = 1
        const val GRID: Int = 2
        const val STAGGERED_GRID: Int = 3
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        when (type) {
            LayoutManagerType.LINEAR -> recyclerView.layoutManager = LinearLayoutManager(context)
            LayoutManagerType.GRID -> recyclerView.layoutManager = GridLayoutManager(context, 2)
            LayoutManagerType.STAGGERED_GRID -> recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var res = R.layout.item
        if (type == LayoutManagerType.STAGGERED_GRID) {
            res = R.layout.staggered_item
        }
        val itemView = LayoutInflater.from(context)
            .inflate(res, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = data[position].name
        var src = data[position].landscapeImageUri
        if (type != LayoutManagerType.LINEAR)
            src = data[position].portraitImageUri
        Utils.setSourceForImageView(context, holder.imgImage, src.removePrefix("assets/"))
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val imgImage: ImageView = itemView.findViewById(R.id.imgImage)
    }
}
package com.example.advancedandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        updateRecyclerView(SuperHeroRecyclerViewAdapter.LINEAR)
    }

    private fun updateRecyclerView(type: Int) {
        recyclerView.adapter = SuperHeroRecyclerViewAdapter((MockSuperHeroRepository()).getAll(), this, type)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.linear_layout -> updateRecyclerView(SuperHeroRecyclerViewAdapter.LINEAR)
            R.id.grid_layout -> updateRecyclerView(SuperHeroRecyclerViewAdapter.GRID)
            R.id.staggered_grid_layout -> updateRecyclerView(SuperHeroRecyclerViewAdapter.STAGGERED_GRID)
        }
        return super.onOptionsItemSelected(item)
    }
}

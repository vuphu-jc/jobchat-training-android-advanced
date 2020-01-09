package com.example.advancedandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        updateRecyclerView(SuperHeroRecyclerViewAdapter.LayoutManagerType.LINEAR)
    }

    private fun updateRecyclerView(type: SuperHeroRecyclerViewAdapter.LayoutManagerType) {
        recyclerView.adapter = SuperHeroRecyclerViewAdapter((MockSuperHeroRepository(this.baseContext)).getAll(), this, type)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.linear_layout -> updateRecyclerView(SuperHeroRecyclerViewAdapter.LayoutManagerType.LINEAR)
            R.id.grid_layout -> updateRecyclerView(SuperHeroRecyclerViewAdapter.LayoutManagerType.GRID)
            R.id.staggered_grid_layout -> updateRecyclerView(SuperHeroRecyclerViewAdapter.LayoutManagerType.STAGGERED_GRID)
        }
        return super.onOptionsItemSelected(item)
    }
}

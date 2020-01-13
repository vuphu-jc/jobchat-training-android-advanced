package com.example.advancedandroid

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    companion object {
        const val ACCESS_EXTERNAL_STORAGE_REQUEST_CODE = 1
        const val SPAN_COUNT = 2
    }

    private lateinit var repository: ImageUriRepository
    private lateinit var rvImages: RecyclerView
    private lateinit var adapter: ImageRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                ACCESS_EXTERNAL_STORAGE_REQUEST_CODE
            )
        }
        else
            initialize()
    }

    private fun initialize() {
        repository = DeviceImageUriRepository(this)
        rvImages = findViewById(R.id.rvImages)
        rvImages.layoutManager = GridLayoutManager(this, SPAN_COUNT)
        adapter = ImageRecyclerViewAdapter(this, mutableListOf())
        adapter.setSpanCount(SPAN_COUNT)
        rvImages.adapter = adapter
        adapter.addData(repository.getImageUri())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_EXTERNAL_STORAGE_REQUEST_CODE
            && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                initialize()
    }
}

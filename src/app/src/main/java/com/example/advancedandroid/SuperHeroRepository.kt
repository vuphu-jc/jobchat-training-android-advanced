package com.example.advancedandroid

import android.content.Context
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap

interface SuperHeroRepository {
    fun getAll() : MutableList<SuperHero>
}

class MockSuperHeroRepository(val context: Context): SuperHeroRepository {

    companion object {
        private const val FILE_NAME: String = "super-hero-data.json"
    }

    override fun getAll(): MutableList<SuperHero> {
        val result = mutableListOf<SuperHero>()
        val content = Utils.getTextFromRaw(context, FILE_NAME)
        val parser = Gson()
        val elements = parser.fromJson(content, arrayListOf<LinkedTreeMap<String, Any>>()::class.java)
        for (e in elements) {
            result.add(SuperHero(e["name"].toString(),
                e["imageUri"].toString(),
                e["description"].toString(),
                e["url"].toString()))
        }
        return result
    }

}
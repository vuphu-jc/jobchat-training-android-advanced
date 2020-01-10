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
        private const val SUPER_HERO_NAME: String = "name"
        private const val SUPER_HERO_IMAGE_URI: String = "imageUri"
        private const val SUPER_HERO_DESCRIPTION: String = "description"
        private const val SUPER_HERO_URL: String = "url"
    }

    override fun getAll(): MutableList<SuperHero> {
        val result = mutableListOf<SuperHero>()
        val content = Utils.getTextFromRaw(context, FILE_NAME)
        val parser = Gson()
        val elements = parser.fromJson(content, arrayListOf<LinkedTreeMap<String, Any>>()::class.java)
        for (e in elements) {
            result.add(SuperHero(e[SUPER_HERO_NAME].toString(),
                e[SUPER_HERO_IMAGE_URI].toString(),
                e[SUPER_HERO_DESCRIPTION].toString(),
                e[SUPER_HERO_URL].toString()))
        }
        return result
    }

}
package com.example.advancedandroid

import com.google.gson.annotations.SerializedName

data class SuperHero(
    @SerializedName("name") val name: String,
    @SerializedName("landscapeImageUri") val landscapeImageUri: String,
    @SerializedName("portraitImageUri") val portraitImageUri: String) {}
package com.example.advancedandroid.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Song(val name: String, val artist: String, val uri: String) : Parcelable {
}
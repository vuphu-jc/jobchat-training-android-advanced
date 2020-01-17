package com.example.advancedandroid.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Song")
@Parcelize
data class Song(
    @ColumnInfo(name="Name") var name: String = "",
    @ColumnInfo(name="Artist") var artist: String = "",
    @ColumnInfo(name="LocalUri") var localUri: String = "",
    @ColumnInfo(name="OnlineUri") var onlineUri: String = "",
    @ColumnInfo(name="ID") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name="VttUrl") var vttUrl: String = "",
    @ColumnInfo(name="Url") var url: String = "",
    @ColumnInfo(name="ImageUrl") var imageUrl: String = "",
    @ColumnInfo(name="Lyrics") var lyrics: String = "",
    @ColumnInfo(name="Chords") var chords: String = ""
     ) : Parcelable {
}
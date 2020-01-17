package com.example.advancedandroid.model

import androidx.room.*
import java.time.temporal.ValueRange

@Dao
interface SongRoomDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(songs: Song): Long

    @Update
    fun update(songs: Song)

    @Delete
    fun delete(songs: Song)

    @Query("SELECT * FROM Song")
    fun selectAll(): List<Song>

    @Query("SELECT * FROM Song WHERE Song.ID = :id LIMIT 1")
    fun selectById(id: Long): Song

    @Query("SELECT * FROM Song WHERE Song.Url = :url LIMIT 1")
    fun selectByUrl(url: String): Song

    @Query("SELECT COUNT(*) FROM Song WHERE Song.Url = :url")
    fun containsUrl(url: String): Int

    @Query("SELECT * FROM Song WHERE Song.Name LIKE '%' || :name || '%' LIMIT :limit")
    fun findByName(name: String, limit: Int): List<Song>
}
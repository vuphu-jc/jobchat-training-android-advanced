package com.example.advancedandroid.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Song::class], version = 2)
abstract class SongRoomDatabase: RoomDatabase() {
    abstract fun SongRoomDAO(): SongRoomDAO

    companion object {
        private const val DATABASE_NAME = "SongRoomDatabase"
        @Volatile
        private var sInstance: SongRoomDatabase? = null

        fun getDatabase(context: Context): SongRoomDatabase {
            val tempInstance = sInstance
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SongRoomDatabase::class.java, DATABASE_NAME).build()
                sInstance = instance
                return instance
            }
        }
    }
}
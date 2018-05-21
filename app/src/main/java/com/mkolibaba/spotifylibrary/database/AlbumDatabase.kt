package com.mkolibaba.spotifylibrary.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.mkolibaba.spotifylibrary.database.dao.AlbumDAO
import com.mkolibaba.spotifylibrary.database.entity.AlbumEntity

@Database(entities = arrayOf(AlbumEntity::class), version = 2)
abstract class AlbumDatabase: RoomDatabase() {
    abstract fun albumDAO(): AlbumDAO

    companion object {
        private var INSTANCE: AlbumDatabase? = null

        fun getInstance(context: Context): AlbumDatabase? {
            if (INSTANCE == null) {
                synchronized(AlbumDatabase::class) {
                    INSTANCE = Room
                            .databaseBuilder(context.applicationContext, AlbumDatabase::class.java, "album.db")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
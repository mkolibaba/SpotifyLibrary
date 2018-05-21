package com.mkolibaba.spotifylibrary.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.mkolibaba.spotifylibrary.database.entity.AlbumEntity

@Dao
interface AlbumDAO {
    @Query("SELECT * FROM Album")
    fun getAll(): List<AlbumEntity>

    @Insert
    fun insert(albumEntity: AlbumEntity)

    @Insert
    fun insertAll(albumEntities: List<AlbumEntity>)
}
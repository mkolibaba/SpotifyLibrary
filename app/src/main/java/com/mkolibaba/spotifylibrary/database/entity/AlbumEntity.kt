package com.mkolibaba.spotifylibrary.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Album")
data class AlbumEntity(
        @PrimaryKey(autoGenerate = true) val id: Long?,
        @ColumnInfo val name: String,
        @ColumnInfo val artist: String,
        @ColumnInfo val imageId: String
)
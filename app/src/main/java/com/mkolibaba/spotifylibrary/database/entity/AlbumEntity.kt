package com.mkolibaba.spotifylibrary.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.graphics.Bitmap

@Entity(tableName = "Album")
data class AlbumEntity(
        @PrimaryKey(autoGenerate = true) val id: Long?,
        @ColumnInfo var name: String,
        @ColumnInfo val artist: String,
        @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val image: ByteArray
)
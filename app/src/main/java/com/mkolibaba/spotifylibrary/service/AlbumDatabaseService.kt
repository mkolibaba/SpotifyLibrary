package com.mkolibaba.spotifylibrary.service

import android.content.Context
import com.mkolibaba.spotifylibrary.database.AlbumDatabase
import com.mkolibaba.spotifylibrary.database.entity.AlbumEntity
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

class AlbumDatabaseService(ctx: Context) {
    var db : AlbumDatabase

    init {
        db = AlbumDatabase.getInstance(ctx)!!
    }

    fun saveAlbum(album: AlbumEntity) {
        launch {
            db.albumDAO().insert(album)
        }
    }

    fun saveAlbums(albums: List<AlbumEntity>) {
        launch {
            db.albumDAO().insertAll(albums)
        }
    }

    fun getAlbums(): List<AlbumEntity> {
        return runBlocking {
            async {
                db.albumDAO().getAll()
            }.await()
        }
    }
}
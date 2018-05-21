package com.mkolibaba.spotifylibrary

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ListView

import com.mkolibaba.spotifylibrary.adapter.SpotifyAlbumAdapter
import com.mkolibaba.spotifylibrary.database.AlbumDatabase
import com.mkolibaba.spotifylibrary.database.entity.AlbumEntity
import com.mkolibaba.spotifylibrary.model.AlbumModel
import com.mkolibaba.spotifylibrary.service.SpotifyService
import com.mkolibaba.spotifylibrary.util.Util
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import java.io.ByteArrayOutputStream

class MainActivity : Activity() {
    private val CLIENT_ID = "9e81ca984c35810c96ab86e5b491"
    private val REDIRECT_URI = "yourcustomprotocol://callback"
    private val REQUEST_CODE = 1337

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = AlbumDatabase.getInstance(this)


        loginSpotify()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        super.onActivityResult(requestCode, resultCode, intent)

        // Check if result comes from the correct activity
        // The next 19 lines of the code are what you need to copy & paste! :)
        if (requestCode == REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, intent)
            loadAlbums(response.accessToken)

            //            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
            //                System.out.println(":)");
            //            }
        }
    }

    private fun loginSpotify() {
        val builder = AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI)
        builder.setScopes(arrayOf("user-library-read"))
        val request = builder.build()
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request)
    }

    private fun loadAlbums(token: String) {
        val service = SpotifyService()
        val albums = service.getAlbums(token)

        val fstAlbum = albums[0]
//        wer(fstAlbum)



//        q()
        val listView = findViewById(R.id.albumListView) as ListView
        listView.adapter = SpotifyAlbumAdapter(this, albums)
    }

    private fun wer(fstAlbum: AlbumModel) {
        val ctx = this
        runBlocking {
            async {
                AlbumDatabase.getInstance(ctx)!!.albumDAO().insert(
                        AlbumEntity(
                                null,
                                fstAlbum.name,
                                fstAlbum.artist,
                                ByteArray(3)))
            }.await()
        }
    }

    private fun q():List<AlbumEntity> {
        val ctx = this
        return runBlocking { async {AlbumDatabase.getInstance(ctx)!!.albumDAO().getAll()}.await() }
    }

    private fun getBytes(bmp: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 0, stream)
        return stream.toByteArray()
    }
}



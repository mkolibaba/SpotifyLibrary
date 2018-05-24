package com.mkolibaba.spotifylibrary

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Button
import android.widget.ListView
import com.mkolibaba.spotifylibrary.adapter.SpotifyAlbumAdapter
import com.mkolibaba.spotifylibrary.model.AlbumModel
import com.mkolibaba.spotifylibrary.service.AlbumDatabaseService
import com.mkolibaba.spotifylibrary.service.SpotifyService
import com.mkolibaba.spotifylibrary.util.Util
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse

class MainActivity : Activity() {
    private val CLIENT_ID = "9e81ca984c35810c96ab86e5b491"
    private val REDIRECT_URI = "spotify://library"
    private val REQUEST_CODE = 1337

    lateinit var albumService : AlbumDatabaseService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1);

        albumService = AlbumDatabaseService(this)

        val syncButton = findViewById(R.id.syncButton) as Button
        syncButton.setOnClickListener { sync() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, intent)
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                loadAlbums(response.accessToken)
            }
        }
    }

    fun sync() {
        loginSpotify()
    }

    fun loginSpotify() {
        val request = AuthenticationRequest
                .Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI)
                .setScopes(arrayOf("user-library-read")).build()
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request)
        // callback uses onActivityResult function
    }

    fun updateDatabase() {
        // TODO
    }

    private fun loadAlbums(token: String) {
        val service = SpotifyService()
        val albums = service.getAlbums(token)

        val fst = albums[0]
        Util.saveCover(
                Util.loadBitmap(fst.imageUrl(AlbumModel.ImageSize.MEDIUM)),
                fst.imageId)


        val listView = findViewById(R.id.albumListView) as ListView
        listView.adapter = SpotifyAlbumAdapter(this, albums)
    }
}



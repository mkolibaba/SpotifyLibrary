package com.mkolibaba.spotifylibrary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

import com.mkolibaba.spotifylibrary.R
import com.mkolibaba.spotifylibrary.model.AlbumModel
import com.mkolibaba.spotifylibrary.util.Util
import com.squareup.picasso.Picasso

class SpotifyAlbumAdapter(context: Context, objects: List<AlbumModel>) : ArrayAdapter<AlbumModel>(context, R.layout.spotify_layout, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)

        val customView = inflater.inflate(R.layout.spotify_layout, parent, false)

        val album = getItem(position)!!

        val artistNameTextView = customView.findViewById(R.id.artistName) as TextView
        val albumNameTextView = customView.findViewById(R.id.albumName) as TextView
        val albumCoverImageView = customView.findViewById(R.id.albumCover) as ImageView

        artistNameTextView.text = album.artist
        albumNameTextView.text = album.name
        Picasso.get().load(album.imageUrl(AlbumModel.ImageSize.SMALL)).into(albumCoverImageView)
//        albumCoverImageView.setImageBitmap(Util.loadRawBitmap(album.imageUrl(AlbumModel.ImageSize.SMALL)))


        return customView
    }
}

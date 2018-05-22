package com.mkolibaba.spotifylibrary.model

import com.mkolibaba.spotifylibrary.util.Util

data class AlbumModel(val artists: List<Artist>, val name: String, val images: List<AlbumImage>) {
    val imageId: String = Util.randomString(15)

    val artist: String
        get() = artists[0].name

    fun imageUrl(imageSize: ImageSize): String {
        return images[imageSize.value].url
    }

    fun albumInfo(): String {
        return "$artist-$name";
    }

    enum class ImageSize(val value: Int) {
        LARGE(0),
        MEDIUM(1),
        SMALL(2)
    }

}

data class Artist(val name: String)

data class AlbumImage(val url: String)
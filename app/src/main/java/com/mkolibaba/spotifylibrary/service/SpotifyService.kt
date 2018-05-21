package com.mkolibaba.spotifylibrary.service

import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.mkolibaba.spotifylibrary.model.AlbumModel
import com.mkolibaba.spotifylibrary.model.SpotifyAlbumsModel
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.collections.HashMap

class SpotifyService {
    fun getAlbums(accessToken: String): List<AlbumModel> {
        val parameters = HashMap<String, Any>()
        parameters["limit"] = 50
        val models = arrayListOf(_getAlbumsModel(accessToken, buildUrl(APIUrl.GET_ALBUMS.url, parameters)))
        while (models.last().next != null) {
            models.add(_getAlbumsModel(accessToken, models.last().next!!))
        }

        return models.flatMap { it.items.map { it.album } }
    }

    private fun _getAlbumsModel(accessToken: String, url: String): SpotifyAlbumsModel {
        val responseFuture = async {
            val request = Request.Builder()
                    .url(url)
                    .headers(Headers.of(
                            "Accept", "application/json",
                            "Content-Type", "application/json",
                            "Authorization", "Bearer $accessToken"
                    ))
                    .build()

            OkHttpClient().newCall(request).execute().body()?.string()
        }

        return runBlocking {
            val body = responseFuture.await()
            Gson().fromJson(body, SpotifyAlbumsModel::class.java)
        }


//        return runBlocking {
//            val (request, response, result) = async {
//                url.httpGet(listOf(
//                        Pair("Accept", "application/json"),
//                        Pair("Content-Type", "application/json"),
//                        Pair("Authorization", "Bearer $accessToken"))).responseString()
//            }.await()
//            Gson().fromJson(result.get(), SpotifyAlbumsModel::class.java)
//        }
    }

    fun buildUrl(url: String, parameters: HashMap<String, Any>): String {
        if (parameters.isEmpty()) {
            return url
        }

        return "$url?" + parameters.entries
                .map {
                    "${it.key}=${it.value}"
                }
                .joinToString("&")
    }

    enum class APIUrl(val url: String) {
        GET_ALBUMS("https://api.spotify.com/v1/me/albums")
    }
}
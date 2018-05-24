package com.mkolibaba.spotifylibrary.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.*

class Util {
    companion object {
        private val SPOTIFY_LIBRARY_FOLDER = "SpotifyLibrary"
        private val COVER_FOLDER = "cover"

        fun loadBitmap(url: String): Bitmap {
            val bitmapFuture = async {
                Picasso.get().load(url).get()
            }

            return runBlocking {
                bitmapFuture.await()
            }
        }

        fun loadRawBitmap(url: String): Bitmap {
            val bitmapFuture = async {
                BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
            }

            return runBlocking {
                bitmapFuture.await()
            }
        }

        fun randomString(length: Int): String {
            val chars = "0123456789QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm"
            val rnd = Random()
            return CharArray(length, { _ -> chars[rnd.nextInt(chars.length)] })
                    .joinToString("")
        }

        fun saveCover(cover: Bitmap, name: String) {
            val dirPath = Environment.getExternalStorageDirectory().toString()
                    .plus("/").plus(SPOTIFY_LIBRARY_FOLDER)
                    .plus("/").plus(COVER_FOLDER)

            val directory = File(dirPath)
            if (!directory.mkdirs()) {
                return
            }

            val file = File(directory, name.plus(".jpg"))
            val stream = FileOutputStream(file)
            stream.use {
                cover.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
            }
        }
    }
}
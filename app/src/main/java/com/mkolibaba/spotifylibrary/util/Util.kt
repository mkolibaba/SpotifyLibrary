package com.mkolibaba.spotifylibrary.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import com.squareup.picasso.Picasso
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import java.net.URL
import java.util.*

class Util {
    companion object {
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
            return IntArray(length, { _ -> rnd.nextInt(chars.length) })
                    .map { chars[it] }.joinToString()
        }
    }
}
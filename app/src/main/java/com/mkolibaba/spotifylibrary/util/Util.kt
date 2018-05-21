package com.mkolibaba.spotifylibrary.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import com.squareup.picasso.Picasso
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import java.net.URL

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
    }
}
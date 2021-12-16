package com.skimani.inventory.utils

import android.content.ContentResolver
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import timber.log.Timber
import java.io.*
import java.lang.Exception

class Util {
    companion object {
        /**
         * Save an image to memory
         */
        fun saveToInternalStorage(context: Context, bitmapImage: Bitmap, name: String): String? {
            val cw = ContextWrapper(context.applicationContext)
            val directory: File = cw.getDir("inventoryDir", Context.MODE_PRIVATE)
            Timber.d("Path>>  $name")
            val mypath = File(directory, name)
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(mypath)
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    fos?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return directory.absolutePath
        }

        /**
         * Load an image from memory
         */
        fun loadImageFromStorage(path: String, view: ImageView) {
            try {
                val f = File(path)
                val b = BitmapFactory.decodeStream(FileInputStream(f))
                view.setImageBitmap(b)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }

        /**
         * Convert a URI to bitmap
         */
        fun uriToBitmap(context: Context, imageUri: Uri): Bitmap? {
            var bitmap: Bitmap? = null
            val contentResolver: ContentResolver = context.contentResolver
            try {
                bitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                } else {
                    val source: ImageDecoder.Source =
                        ImageDecoder.createSource(contentResolver, imageUri)
                    ImageDecoder.decodeBitmap(source)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bitmap
        }
    }
}

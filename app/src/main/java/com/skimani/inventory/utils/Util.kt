package com.skimani.inventory.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skimani.inventory.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val f = File(path)
                    val b = BitmapFactory.decodeStream(FileInputStream(f)) //
                    loadResourceImage(view, b)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
        private fun bitmapToByte(bitmap: Bitmap): ByteArray? {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            return stream.toByteArray()
        }
        fun loadResourceImage(imageView: ImageView, bitmap: Bitmap) {
            GlobalScope.launch(Dispatchers.Main) {
                Glide.with(imageView.context)
                    .load(bitmapToByte(bitmap))
                    .fitCenter()
                    .apply(
                        RequestOptions().placeholder(R.drawable.ic_baseline_sync_24)
                            .error(R.drawable.ic_baseline_image_24)
                    )
                    .into(imageView)
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

        /**
         * Hide and show empty products state
         */
        fun showEmptyState(
            show: Boolean,
            layoutNoSearchResult: RelativeLayout,
            productsRV: RecyclerView
        ) {
            if (show) {
                layoutNoSearchResult.visibility = View.VISIBLE
                productsRV.visibility = View.GONE
            } else {
                layoutNoSearchResult.visibility = View.GONE
                productsRV.visibility = View.VISIBLE
            }
        }

        fun Fragment.hideKeyboard() {
            view?.let { activity?.hideKeyboard(it) }
        }

        fun Activity.hideKeyboard() {
            hideKeyboard(currentFocus ?: View(this))
        }

        fun Context.hideKeyboard(view: View) {
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

package com.example.picturegallery

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.picturegallery.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val url1 =
        "https://pixabay.com/get/g9232de41d04b59d6ec8703f883a9ec44ac808ad6e3548612369e7decd712f7c686f9c1861ec6af25b327cabc3d08edc262005f4d79a66b55d013a84b51b70fa5_640.jpg"
    val url2 =
        "https://pixabay.com/get/g3b6fb15dccaaac3a15d15c9599f3d06ef78e341755e7771518d723d3ac28859675eb6d591e6c919a853f562859833ab422d391aa19a1143b8fa16b9b38d8877c_1280.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.swipeRefreshLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val queue = Volley.newRequestQueue(this)
        binding.swipeRefreshLayout.setOnRefreshListener { loadImage() }
//        val imageLoader = ImageLoader(queue, object : ImageLoader.ImageCache {
//            override fun getBitmap(url: String): Bitmap? {
//                return null
//            }
//
//            override fun putBitmap(url: String, bitmap: Bitmap) {
//            }
//        })
//        imageLoader.get(url, object : ImageLoader.ImageListener {
//            override fun onErrorResponse(error: VolleyError?) {
//                Log.e("bear", "onErrorResponse", error)            }
//
//            override fun onResponse(response: ImageLoader.ImageContainer, isImmediate: Boolean) {
//                binding.imageView.setImageBitmap(response.bitmap)
//            }
//
//        })
//        val stringRequest = StringRequest(
//            Request.Method.GET,
//            url,
//            { response -> binding.textView.text = response }
//        ) { error -> Log.e("bear", "onErrorResponse", error) }
//        queue.add(stringRequest)
    }

    fun loadImage() {
        val random = Random
        val url = if (random.nextBoolean()) url1 else url2
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    if (binding.swipeRefreshLayout.isRefreshing) {
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    if (binding.swipeRefreshLayout.isRefreshing) {
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                    return false
                }

            })
            .into(binding.imageView)

    }
}
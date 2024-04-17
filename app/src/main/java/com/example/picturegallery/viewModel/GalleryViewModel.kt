package com.example.picturegallery.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.picturegallery.model.PhotoItem
import com.example.picturegallery.model.Pixabay
import com.example.picturegallery.model.VolleySingleton
import com.google.gson.Gson

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val _photoListLive = MutableLiveData<List<PhotoItem>>()
    val photoListLive: LiveData<List<PhotoItem>>
        get() = _photoListLive

    fun fetchData() {
        val stringRequest = StringRequest(
            Request.Method.GET,
            getUrl(),
            {
                _photoListLive.value = Gson().fromJson(it, Pixabay::class.java).hits.toList()
            },
            {
                Log.e("bear", it.toString())
            }
        )
        VolleySingleton.getInstance(getApplication()).requestQueue.add(stringRequest)
    }

    private fun getUrl(): String {
        return "https://pixabay.com/api/?key=43332681-4938561e3a0f131a6bc11f317&q=${keyWords.random()}"
    }

    private val keyWords = arrayOf("cat", "dog", "snoopy", "beauty", "phone", "computer", "flower", "animal")
}
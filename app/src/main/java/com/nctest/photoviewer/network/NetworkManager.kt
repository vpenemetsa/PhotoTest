package com.nctest.photoviewer.network

import com.nctest.photoviewer.model.Photo
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class NetworkManager {

    private var photos: ArrayList<Photo> = ArrayList<Photo>()

    companion object {
        private val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val photoService: PhotoService = retrofit.create(PhotoService::class.java)
    }

    fun getData(callback: Callback, refresh: Boolean) {
        if (photos.isNotEmpty() && !refresh) {
            callback.onSuccess(photos, true)
            return
        }

        photoService.photos.enqueue(object : retrofit2.Callback<List<Photo>> {
            override fun onFailure(call: Call<List<Photo>>?, t: Throwable?) {
                callback.onError(t is IOException)
            }

            override fun onResponse(call: Call<List<Photo>>?, response: Response<List<Photo>>?) {
                response?.body()?.let { callback.onSuccess(it as ArrayList<Photo>, false) }
            }
        })
    }

    interface Callback {
        fun onSuccess(photos: ArrayList<Photo>, cached: Boolean)
        fun onError(networkError: Boolean)
    }
}
package com.example.navartlist.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Urls {
    private const val baseUrl = "https://example_Url.org/wp-json/v1/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
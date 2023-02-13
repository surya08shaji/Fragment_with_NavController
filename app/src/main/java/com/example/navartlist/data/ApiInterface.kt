package com.example.navartlist.data

import com.example.navartlist.model.DataModelItem
import com.example.navartlist.model.ShowModelItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("cache/article/list")
   suspend fun getData(
        @Query("postperpage") postperpage: Int?,
        @Query("arg") arg: String?,
        @Query("page") page: Int?,
    ): Response<ArrayList<DataModelItem>>

    @GET("cache/article")
    suspend fun getDetails(
        @Query("article_id") article_id: Int?
    ): Response<ShowModelItem>
}
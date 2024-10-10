package com.example.androidmvvmsample.data.remote

import com.example.androidmvvmsample.data.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/posts")
    suspend fun getAllPosts(): Response<List<Post>>
}
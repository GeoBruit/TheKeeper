package com.example.thekeeper.networking

import com.example.thekeeper.model.GoogleBooksResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApiService {
    @GET("volumes")
    fun searchBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String
    ): Call<GoogleBooksResponse>
}
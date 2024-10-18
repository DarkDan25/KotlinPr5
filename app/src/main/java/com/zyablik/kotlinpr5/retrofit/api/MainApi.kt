package com.zyablik.kotlinpr5.retrofit.api

import com.zyablik.kotlinpr5.model.quote.Quote
import retrofit2.http.GET
import retrofit2.http.Path

interface MainApi {
    @GET("https://dummyjson.com/quotes/{id}")
    suspend fun getQuoteById(@Path("id") id: Int): Quote
}
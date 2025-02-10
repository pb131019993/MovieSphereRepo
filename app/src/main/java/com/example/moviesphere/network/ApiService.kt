package com.example.moviesphere.network

import com.example.moviesphere.beans.response.PopularPeopleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("person/popular")
    suspend fun getPopularPeople(@Query("page") page: Int): PopularPeopleResponse
}
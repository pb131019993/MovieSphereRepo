package com.example.moviesphere.network

import com.example.moviesphere.beans.response.PersonDetails
import com.example.moviesphere.beans.response.PersonImagesResponse
import com.example.moviesphere.beans.response.PopularPeopleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("person/popular")
    suspend fun getPopularPeople(@Query("page") page: Int): PopularPeopleResponse

    @GET("search/person")
    suspend fun searchPeople(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<PopularPeopleResponse>

    @GET("person/{person_id}")
    suspend fun getPersonDetails(@Path("person_id") personId: Int): PersonDetails

    @GET("person/{person_id}/images")
    suspend fun getPersonImages(@Path("person_id") personId: Int): PersonImagesResponse

}
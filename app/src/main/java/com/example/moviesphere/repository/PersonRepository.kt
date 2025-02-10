package com.example.moviesphere.repository

import com.example.moviesphere.beans.response.Person
import com.example.moviesphere.network.RetrofitClient

class PersonRepository {
    private val apiService = RetrofitClient.instance

    suspend fun getPopularPeople(page: Int): List<Person> {
        return apiService.getPopularPeople(page).results
    }
}
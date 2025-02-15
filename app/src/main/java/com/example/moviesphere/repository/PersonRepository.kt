package com.example.moviesphere.repository

import com.example.moviesphere.beans.response.Person
import com.example.moviesphere.beans.response.PersonDetails
import com.example.moviesphere.network.RetrofitClient

class PersonRepository {
    private val apiService = RetrofitClient.instance

    suspend fun getPopularPeople(page: Int): List<Person> {
        return apiService.getPopularPeople(page).results
    }

    suspend fun searchPeople(query: String, page: Int): List<Person> {
        val response = apiService.searchPeople(query, page)
        return if (response.isSuccessful) {
            response.body()?.results ?: emptyList()
        } else {
            emptyList()
        }
    }

    suspend fun getPersonDetails(personId: Int): PersonDetails {
        val details = apiService.getPersonDetails(personId)
        val images = apiService.getPersonImages(personId).profiles.map { it.file_path }
        return details.copy(images = images)
    }
}
package com.example.moviesphere.beans.response

data class PersonDetails(
    val id: Int,
    val name: String,
    val biography: String,
    val profile_path: String?,
    val images: List<String>
)
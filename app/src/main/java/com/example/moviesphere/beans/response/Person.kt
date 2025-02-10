package com.example.moviesphere.beans.response

data class Person(
    val id: Int,
    val name: String,
    val profile_path: String?,
    val known_for_department: String
)
package com.example.moviesphere.beans.response

data class PersonImagesResponse (
    val profiles: List<PersonImage>
)

data class PersonImage(
    val file_path: String
)
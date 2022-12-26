package com.example.m14_retrofit.ui.main

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User (
    @Json(name = "results") val results: List<Result>
)
@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "name") val name: Name,
    @Json(name = "email") val email: String,
    @Json(name = "picture") val picture: Picture
)
@JsonClass(generateAdapter = true)
data class Name(
    @Json(name = "first") val firstName: String,
    @Json(name = "last") val lastName: String
)
@JsonClass(generateAdapter = true)
data class Picture(
    @Json(name = "large") val large: String,
    @Json(name = "medium") val medium: String,
    @Json(name = "thumbnail") val thumbnail: String,
)


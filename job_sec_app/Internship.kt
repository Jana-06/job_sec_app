package com.example.job_sec_app

//data class Internship(
//    val _id: String,
//    val title: String,
//    val company: String,
//    val description: String,
//    val stipend: String,
//    val createdAt: String
//)

data class Internship(
    val _id: String? = null,
    val title: String,
    val company: String,
    val description: String,
    val stipend: String
)

data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null
)
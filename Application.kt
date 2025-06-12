package com.example.job_sec_app

data class ApplicationRequest(
    val name: String,
    val email: String,
    val phone: String,
    val location: String,
    val skills: String,
    val interest: String,
    val resumeUrl: String
)

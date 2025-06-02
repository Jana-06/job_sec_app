package com.example.job_sec_app

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object ApiClient {
    private const val BASE_URL = "mongodb+srv://Janarthan:<db_password>@cluster0.b7etiwe.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

interface ApiService {
    @GET("internships")
    suspend fun getInternships(): List<Internship>

    @POST("internships")
    suspend fun addInternship(@Body internship: Internship): Internship

    @POST("signup")
    suspend fun signUp(@Body user: User): Response<Unit>

    @POST("login")
    suspend fun login(@Body credentials: Map<String, String>): Response<Unit>
}
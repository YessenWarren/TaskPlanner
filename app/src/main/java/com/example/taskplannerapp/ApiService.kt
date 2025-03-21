package com.example.taskplannerapp

import retrofit2.http.GET

interface ApiService {
    @GET("tasks")
    suspend fun getTasks(): List<Task>
}

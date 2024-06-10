package com.example.moodify.retrofit

import com.example.moodify.response.LoginResponse
import com.example.moodify.response.RegisterResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun register(
        @Field("email") email:String,
        @Field("password") password:String
    ): RegisterResponse


    @POST("login")
    suspend fun login(
        @Body requestBody: RequestBody
    ): LoginResponse

    @POST("register")
    suspend fun register(
        @Body requestBody: RequestBody
    ): RegisterResponse
}
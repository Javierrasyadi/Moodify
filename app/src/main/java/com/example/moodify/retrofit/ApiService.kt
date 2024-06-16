package com.example.moodify.retrofit

import com.example.moodify.model.request.AddJournalRequest
import com.example.moodify.model.request.LoginRequest
import com.example.moodify.model.request.SignUpRequest
import com.example.moodify.model.response.AddJournalResponse
import com.example.moodify.model.response.GetDetailJournalResponse
import com.example.moodify.model.response.LoginResponse
import com.example.moodify.model.response.RegisterResponse
import okhttp3.Call
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body requestBody: SignUpRequest
    ): RegisterResponse

    @POST("tool/journal")
    suspend fun addJournal(
        @Body request: AddJournalRequest
    ): AddJournalResponse

    @GET("tool/journal")
    fun getJournal(): retrofit2.Call<GetDetailJournalResponse>
}
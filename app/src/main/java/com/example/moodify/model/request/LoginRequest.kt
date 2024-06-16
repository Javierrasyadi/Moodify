package com.example.moodify.model.request

import com.example.moodify.model.response.StsTokenManagerLogin

data class LoginRequest (
    val email: String,
    val password: String
)
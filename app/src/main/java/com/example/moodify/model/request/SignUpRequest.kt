package com.example.moodify.model.request

data class SignUpRequest (
    val name: String,
    val email: String,
    val password: String
)
package com.example.moodify.model.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @field:SerializedName("code")
    val code: Int,
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("error")
    val error: String,
    @field:SerializedName("message")
    val message: String? = null
)

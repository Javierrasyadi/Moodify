package com.example.moodify.model.request

import com.google.gson.annotations.SerializedName

data class AddJournalRequest (
    @field:SerializedName("journal_title")
    val title: String,
    @field:SerializedName("journal_text")
    val text: String,
    @field:SerializedName("mood")
    val mood: String,
)
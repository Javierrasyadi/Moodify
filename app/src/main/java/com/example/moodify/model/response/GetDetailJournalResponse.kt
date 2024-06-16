package com.example.moodify.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetDetailJournalResponse(
	val journal: List<JournalItem>
)

@Parcelize
data class JournalItem(
	@field:SerializedName("journal_id")
	val journalId: String,
	@field:SerializedName("uid")
	val uid: String,
	@field:SerializedName("createdAt")
	val createdAt: String,
	@field:SerializedName("journal_text")
	val journalText: String,
	@field:SerializedName("journal_title")
	val journalTitle: String,
	@field:SerializedName("updatedAt")
	val updatedAt: String
):Parcelable


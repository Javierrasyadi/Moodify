package com.example.moodify.model.response

data class AddJournalResponse(
	val result: JournalResult? = null,
	val message: String
)

data class Mood(
	val journalId: String? = null,
	val createdAt: String? = null,
	val mood: String? = null,
	val moodLogId: String? = null,
	val updatedAt: String? = null
)

data class JournalResult(
	val journal: Journal? = null,
	val mood: Mood? = null
)

data class Journal(
	val journalId: String? = null,
	val uid: String? = null,
	val createdAt: String? = null,
	val journalText: String? = null,
	val journalTitle: String? = null,
	val updatedAt: String? = null
)


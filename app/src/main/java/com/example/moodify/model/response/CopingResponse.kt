package com.example.moodify.model.response

import com.google.gson.annotations.SerializedName

data class CopingResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("recommendations")
	val recommendations: Recommendations? = null
)

data class Urls(

	@field:SerializedName("music")
	val music: String? = null,

	@field:SerializedName("podcast")
	val podcast: String? = null
)

data class Recommendations(

	@field:SerializedName("urls")
	val urls: Urls? = null,

	@field:SerializedName("text_instruction")
	val textInstruction: String? = null,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: String? = null,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: String? = null
)

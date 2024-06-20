package com.example.moodify.model.response

import com.google.gson.annotations.SerializedName

data class MeditationCategoryResponse(

	@field:SerializedName("recommendations")
	val recommendationsMeditation: RecommendationsMeditation? = null
)

data class MedJoy(

	@field:SerializedName("text_instruction")
	val textInstruction: List<String?>? = null
)

data class MedSadness(

	@field:SerializedName("text_instruction")
	val textInstruction: List<String?>? = null
)

data class MedFear(

	@field:SerializedName("text_instruction")
	val textInstruction: List<String?>? = null
)

data class RecommendationsMeditation(

	@field:SerializedName("love")
	val love: MedLove? = null,

	@field:SerializedName("joy")
	val joy: MedJoy? = null,

	@field:SerializedName("happy")
	val happy: MedHappy? = null,

	@field:SerializedName("neutral")
	val neutral: MedNeutral? = null,

	@field:SerializedName("sadness")
	val sadness: MedSadness? = null,

	@field:SerializedName("anger")
	val anger: MedAnger? = null,

	@field:SerializedName("fear")
	val fear: MedFear? = null
)

data class MedHappy(

	@field:SerializedName("text_instruction")
	val textInstruction: List<String?>? = null
)

data class MedLove(

	@field:SerializedName("text_instruction")
	val textInstruction: List<String?>? = null
)

data class MedNeutral(

	@field:SerializedName("text_instruction")
	val textInstruction: List<String?>? = null
)

data class MedAnger(

	@field:SerializedName("text_instruction")
	val textInstruction: List<String?>? = null
)

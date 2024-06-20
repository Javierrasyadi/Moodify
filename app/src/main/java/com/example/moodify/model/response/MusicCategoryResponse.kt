package com.example.moodify.model.response

import com.google.gson.annotations.SerializedName

data class MusicCategoryResponse(

	@field:SerializedName("recommendations")
	val recommendationsMusic: RecommendationsMusic? = null
)

data class MusAnger(

	@field:SerializedName("music")
	val music: List<String?>? = null,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String?>? = null,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String?>? = null
)

data class MusSadness(

	@field:SerializedName("music")
	val music: List<String?>? = null,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String?>? = null,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String?>? = null
)

data class MusFear(

	@field:SerializedName("music")
	val music: List<String?>? = null,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String?>? = null,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String?>? = null
)

data class RecommendationsMusic(

	@field:SerializedName("love")
	val love: MusLove? = null,

	@field:SerializedName("joy")
	val joy: MusJoy? = null,

	@field:SerializedName("happy")
	val happy: MusHappy? = null,

	@field:SerializedName("neutral")
	val neutral: MusNeutral? = null,

	@field:SerializedName("sadness")
	val sadness: MusSadness? = null,

	@field:SerializedName("anger")
	val anger: MusAnger? = null,

	@field:SerializedName("fear")
	val fear: MusFear? = null
)

data class MusLove(

	@field:SerializedName("music")
	val music: List<String?>? = null,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String?>? = null,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String?>? = null
)

data class MusHappy(

	@field:SerializedName("music")
	val music: List<String?>? = null,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String?>? = null,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String?>? = null
)

data class MusNeutral(

	@field:SerializedName("music")
	val music: List<String?>? = null,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String?>? = null,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String?>? = null
)

data class MusJoy(

	@field:SerializedName("music")
	val music: List<String?>? = null,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String?>? = null,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String?>? = null
)

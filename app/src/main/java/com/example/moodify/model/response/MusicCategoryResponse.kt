package com.example.moodify.model.response

import com.google.gson.annotations.SerializedName

data class MusicCategoryResponse(

	@field:SerializedName("recommendations")
	val recommendationsMusic: RecommendationsMusic
)

data class MusAnger(

	@field:SerializedName("music")
	val music: List<String>,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String>,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String>
)

data class MusSadness(

	@field:SerializedName("music")
	val music: List<String>,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String>,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String>
)

data class MusFear(

	@field:SerializedName("music")
	val music: List<String>,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String>,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String>
)

data class RecommendationsMusic(

	@field:SerializedName("love")
	val love: MusLove,

	@field:SerializedName("joy")
	val joy: MusJoy,

	@field:SerializedName("happy")
	val happy: MusHappy,

	@field:SerializedName("neutral")
	val neutral: MusNeutral,

	@field:SerializedName("sadness")
	val sadness: MusSadness,

	@field:SerializedName("anger")
	val anger: MusAnger,

	@field:SerializedName("fear")
	val fear: MusFear
)

data class MusLove(

	@field:SerializedName("music")
	val music: List<String>,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String>,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String>
)

data class MusHappy(

	@field:SerializedName("music")
	val music: List<String>,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String>,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String>
)

data class MusNeutral(

	@field:SerializedName("music")
	val music: List<String>,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String>,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String>
)

data class MusJoy(

	@field:SerializedName("music")
	val music: List<String>,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String>,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String>
)

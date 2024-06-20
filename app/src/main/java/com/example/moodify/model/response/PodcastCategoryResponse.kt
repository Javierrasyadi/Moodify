package com.example.moodify.model.response

import com.google.gson.annotations.SerializedName

data class PodcastCategoryResponse(

	@field:SerializedName("recommendations")
	val recommendationsPodcast: RecommendationsPodcast? = null
)

data class RecommendationsPodcast(

	@field:SerializedName("love")
	val love: PodLove? = null,

	@field:SerializedName("joy")
	val joy: PodJoy? = null,

	@field:SerializedName("happy")
	val happy: PodHappy? = null,

	@field:SerializedName("neutral")
	val neutral: PodNeutral? = null,

	@field:SerializedName("sadness")
	val sadness: PodSadness? = null,

	@field:SerializedName("anger")
	val anger: PodAnger? = null,

	@field:SerializedName("fear")
	val fear: PodFear? = null
)

data class PodNeutral(

	@field:SerializedName("podcast")
	val podcast: List<String?>? = null,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String?>? = null,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String?>? = null
)

data class PodHappy(

	@field:SerializedName("podcast")
	val podcast: List<String?>? = null,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String?>? = null,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String?>? = null
)

data class PodSadness(

	@field:SerializedName("podcast")
	val podcast: List<String?>? = null,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String?>? = null,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String?>? = null
)

data class PodAnger(

	@field:SerializedName("podcast")
	val podcast: List<String?>? = null,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String?>? = null,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String?>? = null
)

data class PodLove(

	@field:SerializedName("podcast")
	val podcast: List<String?>? = null,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String?>? = null,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String?>? = null
)

data class PodFear(

	@field:SerializedName("podcast")
	val podcast: List<String?>? = null,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String?>? = null,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String?>? = null
)

data class PodJoy(

	@field:SerializedName("podcast")
	val podcast: List<String?>? = null,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String?>? = null,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String?>? = null
)

package com.example.moodify.model.response

import com.google.gson.annotations.SerializedName

data class PodcastCategoryResponse(

	@field:SerializedName("recommendations")
	val recommendationsPodcast: RecommendationsPodcast
)

data class RecommendationsPodcast(

	@field:SerializedName("love")
	val love: PodLove,

	@field:SerializedName("joy")
	val joy: PodJoy,

	@field:SerializedName("happy")
	val happy: PodHappy,

	@field:SerializedName("neutral")
	val neutral: PodNeutral,

	@field:SerializedName("sadness")
	val sadness: PodSadness,

	@field:SerializedName("anger")
	val anger: PodAnger,

	@field:SerializedName("fear")
	val fear: PodFear
)

data class PodNeutral(

	@field:SerializedName("podcast")
	val podcast: List<String>,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String>,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String>
)

data class PodHappy(

	@field:SerializedName("podcast")
	val podcast: List<String>,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String>,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String>
)

data class PodSadness(

	@field:SerializedName("podcast")
	val podcast: List<String>,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String>,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String>
)

data class PodAnger(

	@field:SerializedName("podcast")
	val podcast: List<String>,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String>,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String>
)

data class PodLove(

	@field:SerializedName("podcast")
	val podcast: List<String>,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String>,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String>
)

data class PodFear(

	@field:SerializedName("podcast")
	val podcast: List<String>,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String>,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String>
)

data class PodJoy(

	@field:SerializedName("podcast")
	val podcast: List<String>,

	@field:SerializedName("text_affirmation_first")
	val textAffirmationFirst: List<String>,

	@field:SerializedName("text_affirmation_last")
	val textAffirmationLast: List<String>
)

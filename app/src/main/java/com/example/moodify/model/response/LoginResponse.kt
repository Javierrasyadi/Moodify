package com.example.moodify.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
	val message: String,
	val user: UserLogin
)

data class UserLogin(
	val uid: String,
	val emailVerified: Boolean,
	val createdAt: String,
	val isAnonymous: Boolean,
	val stsTokenManager: StsTokenManagerLogin,
	val lastLoginAt: String,
	val apiKey: String,
	val providerData: List<ProviderDataItemLogin>,
	val appName: String,
	val email: String
)

data class StsTokenManagerLogin(
	val expirationTime: Long,
	val accessToken: String,
	val refreshToken: String
)

data class ProviderDataItemLogin(
	val uid: String,
	val photoURL: Any,
	val phoneNumber: Any,
	val providerId: String,
	val displayName: String,
	val email: String,
)

data class UnauthorizedResponse(
	val code: Int? = null,
	val errorCode: String? = null,
	val error: String? = null,
	val status: String? = null
)


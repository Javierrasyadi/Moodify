package com.example.moodify.model.response

data class RegisterResponse(
	val message: String? = null,
	val user: UserRegister? = null
)

data class StsTokenManagerRegister(
	val expirationTime: Long? = null,
	val accessToken: String? = null,
	val refreshToken: String? = null
)

data class ProviderDataItemRegister(
	val uid: String? = null,
	val photoURL: Any? = null,
	val phoneNumber: Any? = null,
	val providerId: String? = null,
	val displayName: Any? = null,
	val email: String? = null
)

data class UserRegister(
	val uid: String? = null,
	val emailVerified: Boolean? = null,
	val createdAt: String? = null,
	val isAnonymous: Boolean? = null,
	val stsTokenManager: StsTokenManagerRegister? = null,
	val lastLoginAt: String? = null,
	val apiKey: String? = null,
	val providerData: List<ProviderDataItemRegister?>? = null,
	val appName: String? = null,
	val email: String? = null
)


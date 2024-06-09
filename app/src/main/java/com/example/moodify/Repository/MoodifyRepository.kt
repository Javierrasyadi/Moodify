package com.example.moodify.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.moodify.response.LoginResponse
import com.example.moodify.response.RegisterResponse
import com.example.moodify.response.Result
import com.example.moodify.retrofit.ApiService
import com.example.moodify.retrofit.Preference
import com.example.moodify.retrofit.UserModel
import com.example.moodify.viewModel.ViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException

class MoodifyRepository private constructor(
    private val userPreference: Preference,
    private val apiService: ApiService
) {
    private val resultApi = MutableLiveData<Result<String>>()
    suspend fun login(email: String, password: String): LiveData<Result<String>> {
        try {
            val mediaType = "application/json".toMediaType()
            val body = "{\n    \"email\": \"entdecker000@gmail.com\",\n    \"password\": \"jangkrik123\"\n}".toRequestBody(mediaType)
            val successResponse = apiService.login(body)
            Log.d(TAG, "login: $successResponse")
            val user = UserModel(successResponse.user.email, successResponse.user.uid, true)
            Log.d(TAG + "cek token", successResponse.user.uid)
            userPreference.saveSession(user)
            resultApi.value = Result.Success(successResponse.message)
            // menghapus token yang kosong ketika login agar ketika pemanggilan selanjutnya memanggil instance lagi agar mendapatkan token yang baru
            instance = null
            ViewModelFactory.removeInstance()

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            resultApi.value = Result.Error(errorResponse.message)
            Log.d(TAG, "error: ${errorResponse.message}")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "timeout: ${e.message}")
            resultApi.value = Result.Error("Timeout")
        }
        return resultApi
    }

    suspend fun signUp(name: String, email: String, password: String): LiveData<Result<String>> {
        try {
            val mediaType = "application/json".toMediaType()
            val body = "{\n    \"email\": \"jauza1906@gmail.com\",\n    \"password\": \"jauzaarya\"\n}".toRequestBody(mediaType)
            val response = apiService.register(body)
            resultApi.value = Result.Success(response.message!!)
            Log.d(TAG, "pendaftaran berhasil")
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val responseError = Gson().fromJson(errorBody, RegisterResponse::class.java)
            resultApi.value = Result.Error(responseError.message!!)
            Log.e(TAG, "error signup $responseError")
        }
        return resultApi
    }


    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
        instance = null
        ViewModelFactory.removeInstance()
    }

    companion object {
        @Volatile
        private var instance: MoodifyRepository? = null
        private const val TAG = "userRepository"
        fun getInstance(
            apiService: ApiService,
            userPreference: Preference
        ): MoodifyRepository =
            instance ?: synchronized(this) {
                instance ?: MoodifyRepository(userPreference, apiService)
            }.also { instance = it }
    }
}
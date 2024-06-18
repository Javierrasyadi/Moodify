package com.example.moodify.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.moodify.model.request.AddJournalRequest
import com.example.moodify.model.request.LoginRequest
import com.example.moodify.model.request.SignUpRequest
import com.example.moodify.model.response.AddJournalResponse
import com.example.moodify.model.response.CopingResponse
import com.example.moodify.model.response.ErrorResponse
import com.example.moodify.model.response.GetDetailJournalResponse
import com.example.moodify.model.response.JournalItem
import com.example.moodify.model.response.RegisterResponse
import com.example.moodify.model.response.Result
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MoodifyRepository private constructor(
    private val userPreference: Preference,
    private var apiService: ApiService
) {
    private val resultApi = MutableLiveData<Result<String>>()
    private val resultJournal = MutableLiveData<Result<List<JournalItem>>>()
    private val resultCoping = MutableLiveData<Result<CopingResponse>>()
    suspend fun login(email: String, password: String): LiveData<Result<String>> {
        try {
            val request = LoginRequest(email, password)
            val successResponse = apiService.login(request)
            Log.d(TAG, "login: $successResponse")
            val user = UserModel(
                successResponse.user.email,
                successResponse.user.stsTokenManager.accessToken,
                successResponse.user.providerData[0].displayName ?: "User",
                true
            )
            Log.d(TAG + "cek token", successResponse.user.uid)
            userPreference.saveSession(user)
            resultApi.value = Result.Success(successResponse.message)
            // menghapus token yang kosong ketika login agar ketika pemanggilan selanjutnya memanggil instance lagi agar mendapatkan token yang baru
            instance = null
            ViewModelFactory.removeInstance()

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            resultApi.value = Result.Error(errorResponse.message ?: errorResponse.error)
            Log.d(TAG, "error: $errorResponse")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "timeout: ${e.message}")
            resultApi.value = Result.Error("Timeout")
        }
        return resultApi
    }

    suspend fun signUp(name: String, email: String, password: String): LiveData<Result<String>> {
        try {
            val request = SignUpRequest(name, email, password)
            val response = apiService.register(request)
            resultApi.value = Result.Success(response.message!!)
            Log.d(TAG, "pendaftaran berhasil")
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            resultApi.value = Result.Error(errorResponse.message ?: errorResponse.error)
            Log.e(TAG, "error signup $errorResponse")
        }
        return resultApi
    }

    fun getJournal(): LiveData<Result<List<JournalItem>>> {
        resultJournal.value = Result.Loading
        val client = apiService.getJournal()
        client.enqueue(object : Callback<GetDetailJournalResponse> {
            override fun onResponse(
                call: Call<GetDetailJournalResponse>,
                response: Response<GetDetailJournalResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val sortedItems = sortJournalItemsByUpdatedAt(it.journal)
                        resultJournal.value = Result.Success(sortedItems)
                    }
                } else {
                    resultJournal.value = Result.Error("GetStories Error: Cant Retrieve data")
                }
            }

            override fun onFailure(p0: Call<GetDetailJournalResponse>, p1: Throwable) {
                resultJournal.value = Result.Error("GetStories Error: Cant Retrieve data")
            }
        })
        return resultJournal
    }

    suspend fun addJournal(
        title: String,
        desc: String,
        mood: String
    ): LiveData<Result<String>> {
        try {
            val journal = AddJournalRequest(title, desc, mood)
            val response = apiService.addJournal(journal)
            resultApi.value = Result.Success(response.message)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            resultApi.value = Result.Error(errorResponse.message ?: errorResponse.error)
            Log.d(TAG, "error: ${errorResponse.message ?: errorResponse.error}")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "timeout: ${e.message}")
            resultApi.value = Result.Error("Timeout")
        }
        return resultApi
    }

    fun getCoping(): LiveData<Result<CopingResponse>> {
        val resultCoping = MutableLiveData<Result<CopingResponse>>()
        resultCoping.value = Result.Loading
        val client = apiService.getCoping()
        client.enqueue(object : Callback<CopingResponse> {
            override fun onResponse(call: Call<CopingResponse>, response: Response<CopingResponse>) {
                if (response.isSuccessful) {
                    resultCoping.value = Result.Success(response.body()!!)
                } else {
                    resultCoping.value = Result.Error("GetCoping Error: Cant Retrieve data")
                }
            }

            override fun onFailure(call: Call<CopingResponse>, t: Throwable) {
                resultCoping.value = Result.Error("GetCoping Error: Cant Retrieve data")
            }
        })
        return resultCoping
    }


    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    private fun sortJournalItemsByUpdatedAt(journalItems: List<JournalItem>): List<JournalItem> {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        return journalItems.sortedByDescending {
            LocalDateTime.parse(it.updatedAt, formatter)
        }
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
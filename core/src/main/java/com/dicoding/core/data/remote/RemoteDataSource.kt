package com.dicoding.core.data.remote

import android.content.ContentValues
import android.util.Log
import com.dicoding.core.BuildConfig
import com.dicoding.core.data.remote.network.APIResponse
import com.dicoding.core.data.remote.network.APIService
import com.dicoding.core.data.remote.response.MovieDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource(private val apiService: APIService) {

    private val apiKey = BuildConfig.API_KEY

    suspend fun getAllMovie(): Flow<APIResponse<List<MovieDetailResponse>>> {
        return flow {
            try {
                val response = apiService.getAllMovie(apiKey)
                val movieList = response.result
                if (movieList.isNotEmpty()) {
                    emit(APIResponse.Success(response.result))
                } else {
                    emit(APIResponse.Empty)
                }
            } catch (e: Exception) {
                emit(APIResponse.Error(e.toString()))
                Log.e(ContentValues.TAG, "getMovies: $e")
            }
        }.flowOn(Dispatchers.IO)
    }

}
package com.example.data.network

import com.example.shared.models.Data
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET


interface ApiService {
    @GET("u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download")
    fun getData(): Flow<Data>

}
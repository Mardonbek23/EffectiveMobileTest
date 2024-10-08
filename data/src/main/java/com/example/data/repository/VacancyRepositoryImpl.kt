package com.example.data.repository

import android.content.Context
import com.example.data.local.room.AppDatabase
import com.example.data.local.room.dao.VacancyDao
import com.example.data.network.ApiService
import com.example.domain.repository.VacancyRepository
import com.example.shared.models.Data
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VacancyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val context: Context
) :
    VacancyRepository {
    override fun getData(): Flow<Data> {
        return apiService.getData()
    }


}
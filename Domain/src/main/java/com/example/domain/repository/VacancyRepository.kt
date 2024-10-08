package com.example.domain.repository

import com.example.shared.models.Data
import kotlinx.coroutines.flow.Flow


interface VacancyRepository {
    fun getData(): Flow<Data>
}
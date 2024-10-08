package com.example.domain.usecase

import com.example.domain.repository.VacancyRepository
import com.example.shared.models.Vacancy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetVacanciesUC @Inject constructor(private val vacancyRepository: VacancyRepository) {
    operator fun invoke(): Flow<Result<List<Vacancy>>> {
        return vacancyRepository.getData().map {
            Result.success(it.vacancies)
        }.catch {
            emit(Result.failure(it))
        }.flowOn(Dispatchers.IO)
    }
}
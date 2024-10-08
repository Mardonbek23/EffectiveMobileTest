package com.example.effectivemobiletest.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.data.local.room.AppDatabase
import com.example.data.local.room.entities.VacancyEntity
import com.example.domain.usecase.GetVacanciesUC
import com.example.effectivemobiletest.utils.RequestState
import com.example.shared.models.Vacancy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VacancyViewModel @Inject constructor(
    private val vacanciesUC: GetVacanciesUC,
    private val context: Context
) : ViewModel() {

    private val _vacancies = MutableLiveData<RequestState<List<Vacancy>>>()
    val vacancies: LiveData<RequestState<List<Vacancy>>> get() = _vacancies
    fun getVacancies() {
        val vacanciesUC1 = vacanciesUC()
        _vacancies.value = RequestState.Loading
        viewModelScope.launch {
            vacanciesUC1.catch {
                _vacancies.value = RequestState.Error(it.message.toString())
            }.collect {
                it.map { data ->
                    _vacancies.value = RequestState.Success(data)
                }
            }
        }

    }

    val vacanciesCountOnSaved: LiveData<Int> =
        AppDatabase.getInstance(context).vacancyDao().getVacanciesCountOnSaved()

    val getSavedVacancies: LiveData<List<VacancyEntity>> =
        AppDatabase.getInstance(context).vacancyDao().getAllFavouriteVacancies()


}
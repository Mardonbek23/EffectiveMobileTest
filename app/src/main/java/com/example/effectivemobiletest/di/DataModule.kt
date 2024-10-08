package com.example.effectivemobiletest.di

import android.content.Context
import com.example.data.network.ApiService
import com.example.data.repository.VacancyRepositoryImpl
import com.example.domain.repository.VacancyRepository
import com.example.shared.models.Data
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Singleton

@Module(includes = [DataModule.BindModule::class])
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideLocalData(@ApplicationContext context: Context): Data? {
        val jsonString: String
        try {
            jsonString = context.assets.open("data.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return Gson().fromJson(jsonString, Data::class.java)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class BindModule {
        @Binds
        abstract fun bindVacancyRepository(vacancyRepositoryImpl: VacancyRepositoryImpl): VacancyRepository
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}
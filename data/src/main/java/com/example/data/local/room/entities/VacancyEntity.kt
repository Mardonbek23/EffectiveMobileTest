package com.example.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shared.models.Address
import com.example.shared.models.Experience
import com.example.shared.models.Salary
import com.example.shared.models.Vacancy

@Entity
data class VacancyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val address: Address,
    val appliedNumber: Int,
    val company: String,
    val description: String,
    val experience: Experience,
    val isFavorite: Boolean = false,
    val lookingNumber: Int,
    val publishedDate: String,
    val questions: List<String>,
    val responsibilities: String,
    val salary: Salary,
    val schedules: List<String>,
    val title: String
) {
    fun entityToVacancy(): Vacancy {
        return Vacancy(
            this.address,
            this.appliedNumber,
            this.company,
            this.description,
            this.experience,
            this.id,
            this.isFavorite,
            this.lookingNumber,
            this.publishedDate,
            this.questions, this.responsibilities, this.salary, this.schedules, this.title
        )
    }
}